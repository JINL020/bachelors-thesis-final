package com.minimal.ec135.screenSettings

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun SettingsButton(text: String) {
    val context = LocalContext.current

    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            val uri = Uri.fromParts("package", "com.minimal.ec135", null)
            val intent =
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
            context.startActivity(intent)
        }
    ) {
        Text(text = text)
    }
}