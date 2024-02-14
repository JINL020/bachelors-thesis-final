package com.minimal.ec135.objectDetection

import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.minimal.ec135.MainViewModel
import com.minimal.ec135.screenLicence.LicencePlateRowItem
import com.minimal.ec135.util.round
import java.time.LocalDateTime
import kotlin.math.max
import kotlin.math.min

val ocrRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

fun processDetectionResults(myDetections: List<MyDetection>, viewModel: MainViewModel) {
    val TAG = "processDetectionResults"
    val searchNumber = viewModel.inputString
    val ocrThreshold = viewModel.ocrThreshold
    val savedDetections = viewModel.licencePlateRowItems.value
    val similarityThreshold = viewModel.similarityThreshold

    myDetections.forEach { detection ->
        val roi = detection.getRoiBitmap()
        val ocrTask = ocrRecognizer.process(InputImage.fromBitmap(roi, 0))

        ocrTask.addOnSuccessListener { result ->
            val ocrText = getOcrText(result.textBlocks)
            val ocrScore = findSimilarity(searchNumber, result.text)

            val entryExist = savedDetections.any { savedDetection ->
                (savedDetection.searchNumber == searchNumber)
            }

            val shouldAdd = if (!entryExist) {
                true
            } else {
                !savedDetections.any { savedDetection ->
                    Log.d(
                        TAG,
                        "${savedDetection.ocrText} and ${ocrText} are ${
                            findSimilarity(
                                savedDetection.ocrText,
                                ocrText
                            )
                        } similar and treshold is ${similarityThreshold}"
                    )
                    (findSimilarity(
                        savedDetection.ocrText,
                        ocrText
                    ) > similarityThreshold)
                }
            }
            Log.d(TAG, "shouldAdd is ${shouldAdd}")
            Log.d(TAG, "ocrScore is ${ocrScore} and ocrThreshold is ${ocrThreshold}")

            if (shouldAdd && (ocrScore >= ocrThreshold)) {
                val item = LicencePlateRowItem(
                    searchNumber = searchNumber,
                    licenceBitmap = roi,
                    ocrText = ocrText,
                    ocrScore = ocrScore,
                    objectDetectionScore = detection.score,
                    createdAt = LocalDateTime.now()
                )
                viewModel.addRowItem(item)
            }
        }
    }
}

fun getOcrText(textBlocks: MutableList<Text.TextBlock>): String {
    try {
        val largestLine = textBlocks
            .flatMap { block -> block.lines }
            .maxByOrNull { line -> line.boundingBox?.height() ?: 0 }

        return largestLine?.text.orEmpty().replace("\\s".toRegex(), "")
    } catch (e: Exception) {
        return ""
    }
}

fun findSimilarity(x: String, y: String): Float {
    val maxLength = max(x.length, y.length)
    return if (maxLength > 0) {
        ((maxLength * 1.0 - getLevenshteinDistance(x, y)) / maxLength * 1.0).toFloat().round(2)
    } else 1.0f
}

// Code is based on https://www.techiedelight.com/find-similarities-between-two-strings-in-kotlin/
private fun getLevenshteinDistance(x: String, y: String): Int {
    val m = x.length
    val n = y.length
    val distanceMatrix = Array(m + 1) { IntArray(n + 1) }

    for (i in 1..m) {
        distanceMatrix[i][0] = i
    }

    for (j in 1..n) {
        distanceMatrix[0][j] = j
    }

    for ((i, xChar) in x.withIndex()) {
        for ((j, yChar) in y.withIndex()) {
            val cost = if (xChar == yChar) 0 else 1
            distanceMatrix[i + 1][j + 1] = min(
                min(
                    distanceMatrix[i][j + 1] + 1,
                    distanceMatrix[i + 1][j] + 1
                ),
                distanceMatrix[i][j] + cost
            )
        }
    }

    return distanceMatrix[m][n]
}
