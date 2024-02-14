package com.minimal.ec135.screenLicence

import android.graphics.Bitmap
import java.time.LocalDateTime

data class LicencePlateRowItem(
    val searchNumber: String,
    val licenceBitmap: Bitmap,
    val ocrText: String,
    val ocrScore: Float,
    val objectDetectionScore: Float,
    val createdAt: LocalDateTime
)
