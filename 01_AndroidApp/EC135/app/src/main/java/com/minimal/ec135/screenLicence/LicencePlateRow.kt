package com.minimal.ec135.screenLicence

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun LicencePlateRow(item: LicencePlateRowItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        content = {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                LicencePlateContent(item)
                Image(
                    bitmap = item.licenceBitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(item.licenceBitmap.width.toFloat() / item.licenceBitmap.height)
                )
            }
        }
    )
}

@Composable
private fun LicencePlateContent(item: LicencePlateRowItem) {
    Text(text = item.searchNumber.ifEmpty { "[No LP number provided]" }, fontWeight = FontWeight.Bold)
    Text(text = "ocr: ${item.ocrText}")
    Text(text = "ocr score: ${item.ocrScore}")
    Text(text = "object detection score: ${item.objectDetectionScore}")
    Text(text = "${item.createdAt}")
}
