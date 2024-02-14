package com.minimal.ec135.screenScanner

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.minimal.ec135.MainViewModel

@Composable
fun InputStringScreen(navController: NavHostController) {
    val TAG = "StringInputScreen"
    Log.d(TAG, "Init StringInputScreen")

    val context = LocalContext.current
    val viewModel = viewModel<MainViewModel>(context as ComponentActivity)

    var input by remember { mutableStateOf("") }

    fun onEnterButtonClick() {
        viewModel.inputString = input
        navController.navigate("cameraScreen")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = "Please enter the licence plate number you want to find."
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp),
            singleLine = true,
            value = input,
            onValueChange = { input = it.replace("\\s".toRegex(), "") },
            enabled = true,
            placeholder = { Text("WJULIA4") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,
            )
        )
        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = { onEnterButtonClick() }
        ) {
            Text("Enter")
        }
    }
}