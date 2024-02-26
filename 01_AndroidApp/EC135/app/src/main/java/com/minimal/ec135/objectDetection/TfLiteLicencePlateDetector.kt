package com.minimal.ec135.objectDetection

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.minimal.ec135.ml.LicenceModelNew
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp

class TfLiteLicencePlateDetector(
    private val context: Context,
    private val threshold: Float = 0.5f,
) {
    private val TAG = "TfLiteLicencePlateDetector"

    private var tfLiteModel: LicenceModelNew = LicenceModelNew.newInstance(context)
    private var imageProcessor: ImageProcessor = ImageProcessor.Builder()
        .add(ResizeOp(320, 320, ResizeOp.ResizeMethod.BILINEAR))
        .build()
    //private val labels: List<String> = FileUtil.loadLabels(context, "labels.txt")

    fun detect(bitmap: Bitmap): List<MyDetection> {
        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(bitmap))

        val outputs = tfLiteModel.process(tensorImage)
        val locations = outputs.locationsAsTensorBuffer.floatArray
        val scores = outputs.scoresAsTensorBuffer.floatArray
            //.map { it.round(2) }
            //.toFloatArray()

        return createDetections(bitmap, locations, scores)
    }

    private fun createDetections(
        bitmap: Bitmap,
        locations: FloatArray,
        scores: FloatArray
    ): List<MyDetection> {
        val h = bitmap.height
        val w = bitmap.width
        var x = 0

        val myDetections = mutableListOf<MyDetection>()
        scores.forEachIndexed { index, score ->
            if (score > threshold) {
                Log.d(TAG,"$score is bigger than $threshold")

                x = index * 4

                val left = locations[x + 1] * w
                val top = locations[x] * h
                val right = locations[x + 3] * w
                val bottom = locations[x + 2] * h

                val myDetection = MyDetection(
                    left = left,
                    top = top,
                    right = right,
                    bottom = bottom,
                    score = score,
                    fullBitmap = bitmap,
                )
                myDetections.add(myDetection)
            }
        }
        return myDetections;
    }
}
