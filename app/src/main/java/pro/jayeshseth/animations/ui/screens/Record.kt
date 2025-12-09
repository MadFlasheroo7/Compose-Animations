package pro.jayeshseth.animations.ui.screens

import android.Manifest
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import androidx.annotation.RequiresPermission
import com.musicg.api.ClapApi
import com.musicg.wave.WaveHeader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext


@RequiresPermission(Manifest.permission.RECORD_AUDIO)
suspend fun detectClapsDebug(
    onClapDetected: () -> Unit,
    onLog: (String) -> Unit,
    onError: (String) -> Unit
) {
    withContext(Dispatchers.IO) {
        try {
            val sampleRate = 44100
            onLog("Sample Rate: $sampleRate Hz")

            val minBufferSize = AudioRecord.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )

            if (minBufferSize == AudioRecord.ERROR || minBufferSize == AudioRecord.ERROR_BAD_VALUE) {
                onError("Invalid buffer size: $minBufferSize")
                return@withContext
            }

            val bufferSize = nextPowerOf2(minBufferSize)

            onLog("Min Buffer Size: $minBufferSize bytes")
            onLog("Adjusted Buffer Size: $bufferSize bytes (power of 2)")

            val audioRecord = try {
                AudioRecord(
                    MediaRecorder.AudioSource.MIC,
                    sampleRate,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    bufferSize
                )
            } catch (e: Exception) {
                onError("Failed to create AudioRecord: ${e.message}")
                return@withContext
            }

            if (audioRecord.state != AudioRecord.STATE_INITIALIZED) {
                onError("AudioRecord not initialized. State: ${audioRecord.state}")
                audioRecord.release()
                return@withContext
            }

            onLog("AudioRecord initialized successfully")

            val waveHeader = WaveHeader().apply {
                channels = 1
                bitsPerSample = 16
                this.sampleRate = sampleRate
            }

            onLog("WaveHeader configured")

            val clapApi = try {
                ClapApi(waveHeader)
            } catch (e: Exception) {
                onError("Failed to create ClapApi: ${e.message}")
                audioRecord.release()
                return@withContext
            }

            onLog("ClapApi initialized")

            val buffer = ByteArray(bufferSize)

            audioRecord.startRecording()
            onLog("Recording started")

            var frameCount = 0
            var lastClapTime = 0L

            try {
                while (currentCoroutineContext().isActive) {
                    val read = audioRecord.read(buffer, 0, 2048)

                    if (read < 0) {
                        onLog("Read error: $read")
                        delay(100)
                        continue
                    }

                    frameCount++

                    if (frameCount % 100 == 0) {
                        onLog("Frames processed: $frameCount, Bytes read: $read")
                    }

                    try {
                        val isClap = clapApi.isClap(buffer)

                        if (isClap) {
                            val now = System.currentTimeMillis()
                            if (now - lastClapTime > 100) {
                                lastClapTime = now
                                onLog("CLAP DETECTED! Frame: $frameCount")
                                withContext(Dispatchers.Main) {
                                    onClapDetected()
                                }
                            }
                        }
                    } catch (e: Exception) {
                        onLog("Error checking clap: ${e.message}")
                    }

                    delay(10)
                }
            } finally {
                onLog("Stopping recording...")
                audioRecord.stop()
                audioRecord.release()
                onLog("Recording stopped and released")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Fatal error", e)
            onError("Fatal error: ${e.message}")
        }
    }
}

private const val TAG = "ClapDetector"

private fun nextPowerOf2(n: Int): Int {
    var value = n - 1
    value = value or (value shr 1)
    value = value or (value shr 2)
    value = value or (value shr 4)
    value = value or (value shr 8)
    value = value or (value shr 16)
    return value + 1
}