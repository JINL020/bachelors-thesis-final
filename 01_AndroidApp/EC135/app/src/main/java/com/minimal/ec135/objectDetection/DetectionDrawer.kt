package com.minimal.ec135.objectDetection

import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log

val boxPaint = Paint().apply {
    style = Paint.Style.STROKE
    isAntiAlias = true
    color = Color.BLUE
}

val textPaint = Paint().apply {
    isAntiAlias = true
    textAlign = Paint.Align.LEFT
    color = Color.BLUE
}

fun drawDetection(
    canvas: android.graphics.Canvas,
    detection: MyDetection,
    rotation: Float = 0f,
    scalingFactor: Float = 1f,
) {
    val TAG = "drawDetection"
    Log.d(TAG, "Start drawDetection")

    val rotatedRect = calculateRotatedRect(detection, rotation, scalingFactor)

    boxPaint.strokeWidth = canvas.width / 118f
    textPaint.textSize = canvas.width / 18f

    canvas.drawRect(rotatedRect, boxPaint)

    canvas.drawText(
        "${detection.score}",
        rotatedRect.left,
        rotatedRect.top,
        textPaint
    )
}

private fun calculateRotatedRect(
    detection: MyDetection,
    rotation: Float,
    scalingFactor: Float,
): RectF {
    return if (rotation == 90f) {
        RectF(
            detection.left * scalingFactor,
            detection.top * scalingFactor,
            detection.right * scalingFactor,
            detection.bottom * scalingFactor

        )
    } else {
        val w = detection.fullBitmap.width
        val h = detection.fullBitmap.height

        when (rotation) {
            0f -> RectF(
                (h - detection.bottom) * scalingFactor,
                detection.left * scalingFactor,
                (h - detection.top) * scalingFactor,
                detection.right * scalingFactor
            )

            90f -> RectF(
                detection.top * scalingFactor,
                (w - detection.left) * scalingFactor,
                detection.bottom * scalingFactor,
                (w - detection.right) * scalingFactor
            )

            180f -> RectF(
                detection.top * scalingFactor,
                (w - detection.right) * scalingFactor,
                detection.bottom * scalingFactor,
                (w - detection.left) * scalingFactor
            )

            // 270f
            else -> RectF(
                (w - detection.right) * scalingFactor,
                (h - detection.bottom) * scalingFactor,
                (w - detection.left) * scalingFactor,
                (h - detection.top) * scalingFactor
            )
        }
    }
}
