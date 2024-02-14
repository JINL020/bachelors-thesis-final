package com.minimal.ec135.objectDetection

import android.graphics.Bitmap

class MyDetection(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
    val score: Float,
    val fullBitmap: Bitmap,
) {
    fun getRoiBitmap(): Bitmap {
        val correctedLeft = left.toInt().coerceIn(0, fullBitmap.width)
        val correctedTop = top.toInt().coerceIn(0, fullBitmap.height)
        val correctedRight = right.toInt().coerceIn(0, fullBitmap.width)
        val correctedBottom = bottom.toInt().coerceIn(0, fullBitmap.height)

        return Bitmap.createBitmap(
            fullBitmap,
            correctedLeft,
            correctedTop,
            correctedRight - correctedLeft,
            correctedBottom - correctedTop
        )
    }
}