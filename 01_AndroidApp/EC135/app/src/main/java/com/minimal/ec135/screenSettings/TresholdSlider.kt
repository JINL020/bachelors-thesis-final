package com.minimal.ec135.screenSettings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minimal.ec135.util.round

@Composable
fun ThresholdSlider(
    name: String,
    initialThreshold: Float,
    onValueChange: (Float) -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(initialThreshold) }
    Card(
        modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth(),
        content = {
            Column(
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
            ) {
                Text(text = "$name: $sliderPosition")
                Slider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it.round(2)
                        onValueChange(sliderPosition)
                    },
                    valueRange = 0f..1f,
                    steps = 99
                )
            }
        }
    )
}