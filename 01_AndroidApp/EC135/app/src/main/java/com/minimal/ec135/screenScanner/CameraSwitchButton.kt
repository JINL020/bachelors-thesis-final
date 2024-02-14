package com.minimal.ec135.screenScanner

import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.minimal.ec135.MainViewModel
import com.minimal.ec135.R

@Composable
fun CameraSwitchButton(cameraController: LifecycleCameraController) {
    val TAG = "CameraSwitchButton"

    val context = LocalContext.current
    val viewModel = viewModel<MainViewModel>((context as ComponentActivity))

    val iconOffsetX = (-16).dp
    val iconOffsetY = (16).dp

    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .offset(iconOffsetX, iconOffsetY)
    ) {
        IconButton(onClick = {
            if (cameraController.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                viewModel.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                viewModel.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            }
            cameraController.cameraSelector = viewModel.cameraSelector
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_cameraswitch_outline),
                contentDescription = "Switch camera",
            )
        }
    }
}