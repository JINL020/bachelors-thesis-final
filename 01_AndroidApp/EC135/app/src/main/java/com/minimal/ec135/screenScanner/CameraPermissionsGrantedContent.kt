package com.minimal.ec135.screenScanner

import androidx.activity.ComponentActivity
import androidx.camera.core.ImageAnalysis
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.minimal.ec135.MainViewModel
import com.minimal.ec135.objectDetection.DetectionsProcessor
import com.minimal.ec135.objectDetection.LicencePlateImageAnalyzer
import com.minimal.ec135.objectDetection.MyDetection
import com.minimal.ec135.objectDetection.TfLiteLicencePlateDetector

@Composable
fun CameraPermissionsGrantedContent(navController: NavHostController) {
    val TAG = "CameraPermissionsGrantedContent"
    //Log.d(TAG, "Init CameraPermissionsGrantedContent")

    val context = LocalContext.current
    val viewModel = viewModel<MainViewModel>((context as ComponentActivity))

    var myDetections by remember { mutableStateOf(emptyList<MyDetection>()) }
    var imageRotation by remember { mutableFloatStateOf(0f) }
    val detectionsProcessor by remember {mutableStateOf(DetectionsProcessor(context))}

    val analyzer = remember {
        LicencePlateImageAnalyzer(
            detector = TfLiteLicencePlateDetector(context, viewModel.objectDetectionThreshold),
            cameraSelector = viewModel.cameraSelector,
            onResult = { detections, rotation ->
                myDetections = detections
                imageRotation = rotation

                detectionsProcessor.processDetectionResults(myDetections, viewModel)
            }
        )
    }
    analyzer.setCameraSelector(viewModel.cameraSelector)

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
            setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(context),
                analyzer
            )
            imageAnalysisBackpressureStrategy = ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
            cameraSelector = viewModel.cameraSelector
        }
    }

    CameraPreview(
        controller = cameraController,
        detections = myDetections,
        rotation = imageRotation,
        modifier = Modifier
            .aspectRatio(3f / 4f)
            .fillMaxSize()
    )
    CameraSwitchButton(cameraController)
}