package com.minimal.ec135.screenScanner

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.minimal.ec135.MainViewModel
import com.minimal.ec135.objectDetection.MyDetection
import com.minimal.ec135.objectDetection.drawDetection
import kotlin.math.max


@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    detections: List<MyDetection>,
    rotation: Float,
    modifier: Modifier = Modifier
) {
    val TAG = "CameraPreview"
    Log.d(TAG, "Init CameraPreview")

    var previewSize by remember { mutableStateOf(Size.Zero) }
    val viewModel = viewModel<MainViewModel>((LocalContext.current as ComponentActivity))

    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = { context ->
            PreviewView(context).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
                scaleType = PreviewView.ScaleType.FIT_CENTER
            }
        },
        modifier = modifier.onGloballyPositioned { coordinates ->
            previewSize = coordinates.size.toSize()
        }
    )

    detections.forEach { detection ->
        val scalingFactor =
            previewSize.height / max(detection.fullBitmap.height, detection.fullBitmap.width)
        Canvas(
            modifier = modifier
        ) {
            drawIntoCanvas { canvas ->
                drawDetection(
                    canvas.nativeCanvas,
                    detection,
                    rotation,
                    scalingFactor
                )
            }
        }
    }
}


