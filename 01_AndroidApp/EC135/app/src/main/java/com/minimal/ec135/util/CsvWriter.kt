package com.minimal.ec135.util

import android.util.Log
import com.minimal.ec135.screenLicence.LicencePlateRowItem
import java.io.File
import java.io.FileWriter
import java.time.format.DateTimeFormatter

fun writeLicencePlateItemsToCsv(items: List<LicencePlateRowItem>, outputFile: File): String {
    val TAG = "writeLicencePlateItemsToCsv"
    try {
        FileWriter(outputFile).use { writer ->
            writer.append("searchNumber,ocrText,ocrScore,objectDetectionScore,createdAt\n")

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            items.forEach { item ->
                writer.append("${item.searchNumber},")
                    .append("${item.ocrText},")
                    .append("${item.ocrScore},")
                    .append("${item.objectDetectionScore},")
                    .append("${item.createdAt.format(formatter)}\n")
            }
        }
        val message = "CSV file written successfully to $outputFile"
        Log.d(TAG, message)
        return message
    } catch (e: Exception) {
        val message = "An error occurred while writing CSV file: ${e.message}"
        Log.d(TAG, message)
        return message
    }
}