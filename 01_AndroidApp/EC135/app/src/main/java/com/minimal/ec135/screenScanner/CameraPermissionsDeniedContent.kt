package com.minimal.ec135.screenScanner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CameraPermissionsDeniedContent() {
    Box(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxSize(),
    ) {
        Text(text = "Camera permission is needed to access the camera. Please enable it in the app settings.")

    }
}
