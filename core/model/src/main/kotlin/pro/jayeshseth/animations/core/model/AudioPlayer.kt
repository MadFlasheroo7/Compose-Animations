package pro.jayeshseth.animations.core.model

import android.content.Context
import android.media.MediaPlayer

object AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    fun play(context: Context, audioResId: Int) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, audioResId)
            mediaPlayer?.isLooping = true
        }
        mediaPlayer?.start()
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun stop() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}