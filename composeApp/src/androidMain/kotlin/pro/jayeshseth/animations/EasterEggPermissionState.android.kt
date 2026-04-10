package pro.jayeshseth.animations

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import pro.jayeshseth.animations.core.utils.PermissionPrefs
import androidx.core.content.edit

@Composable
actual fun rememberEasterEggPermissionState(): EasterEggPermissionState {
    val context = LocalContext.current
    val prefs = remember {
        context.getSharedPreferences("easter_egg_permission_prefs", Activity.MODE_PRIVATE)
    }

    val state = remember { EasterEggPermissionState() }

    // Initialize from persisted state + current permission status
    remember {
        state.isPermissionGranted = context.checkSelfPermission(
            android.Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
        state.isPermanentlyDenied = prefs.getBoolean(PermissionPrefs.RECORD_AUDIO_DENIED, false)
        state.dontShowEverAgain = prefs.getBoolean(PermissionPrefs.PERMISSION_RATIONAL, false)
        true
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        state.isPermissionGranted = granted
        if (granted) {
            // Permission granted - detection will start via LaunchedEffect in AboutScreen
        } else {
            val activity = context as? Activity
            if (activity != null &&
                !ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, android.Manifest.permission.RECORD_AUDIO
                )
            ) {
                state.isPermanentlyDenied = true
                prefs.edit { putBoolean(PermissionPrefs.RECORD_AUDIO_DENIED, true) }
            }
        }
    }

    state.requestPermission = {
        if (state.isPermanentlyDenied) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        } else {
            launcher.launch(android.Manifest.permission.RECORD_AUDIO)
        }
    }

    state.markDontShowEverAgain = {
        state.dontShowEverAgain = true
        prefs.edit { putBoolean(PermissionPrefs.PERMISSION_RATIONAL, true) }
    }

    state.openAppSettings = {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

    return state
}