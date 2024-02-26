package com.minimal.ec135.objectDetection

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

class LicencePlateImageAnalyzer(
    private val detector: TfLiteLicencePlateDetector,
    private var cameraSelector: CameraSelector,
    private val onResult: (List<MyDetection>, Float) -> Unit,
) : ImageAnalysis.Analyzer {
    private val TAG = "LicencePlateImageAnalyzer"

    override fun analyze(image: ImageProxy) {
        Log.d(TAG, "Received frame with resolution: ${image.width}x${image.height}")

        val rotation = image.imageInfo.rotationDegrees.toFloat()
        val matrix = calculateRotationMatrix(rotation)
        val rotatedBitmap = createRotatedBitmap(image, matrix)

        val results = detector.detect(rotatedBitmap)
        onResult(results, rotation)
        image.close()
    }

    private fun calculateRotationMatrix(rotation: Float): Matrix = Matrix().apply {
        postRotate(rotation)
        if (cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) {
            postScale(-1f, 1f)
        }
    }

    private fun createRotatedBitmap(image: ImageProxy, matrix: Matrix): Bitmap {
        return Bitmap.createBitmap(
            image.toBitmap(),
            0,
            0,
            image.width,
            image.height,
            matrix,
            true
        )
    }

    fun setCameraSelector(newSelector: CameraSelector) {
        cameraSelector = newSelector
    }
}