package com.minimal.ec135.screenSettings

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.minimal.ec135.MainViewModel

@Composable
fun SettingsScreen() {
    val TAG = "SettingsScreen"
    Log.d(TAG, "Init SettingsScreen")

    val context = LocalContext.current
    val viewModel = viewModel<MainViewModel>((context as ComponentActivity))

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        ThresholdSlider(
            "Object Detection Threshold",
            viewModel.objectDetectionThreshold
        ) { sliderPosition ->
            viewModel.objectDetectionThreshold = sliderPosition
        }
        ThresholdSlider(
            "OCR Threshold",
            viewModel.ocrThreshold
        ) { sliderPosition ->
            viewModel.ocrThreshold = sliderPosition
        }
        ThresholdSlider(
            "Similarity Threshold",
            viewModel.similarityThreshold
        ) { sliderPosition ->
            viewModel.similarityThreshold = sliderPosition
        }
        SettingsButton("Open App Info Settings")
    }
}

