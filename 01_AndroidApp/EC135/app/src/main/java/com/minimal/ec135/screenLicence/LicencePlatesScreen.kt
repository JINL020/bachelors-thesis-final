package com.minimal.ec135.screenLicence

import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.minimal.ec135.MainViewModel
import com.minimal.ec135.util.writeLicencePlateItemsToCsv
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun LicencePlatesScreen() {
    val TAG = "LicencePlatesScreen"
    Log.d(TAG, "Init LicencePlatesScreen")

    val context = LocalContext.current

    val viewModel = viewModel<MainViewModel>((LocalContext.current as ComponentActivity))
    val licencePlateRowItems by viewModel.licencePlateRowItems.collectAsState()
    //val licencePlateRowItems = viewModel.licencePlateRowItems

    Column {
        Text(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxWidth(),
            text = "Saved Licence Plates: ${licencePlateRowItems.size}"
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (licencePlateRowItems.isNotEmpty()) {
                        val fileDir =
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")
                        val currentTime = LocalDateTime.now().format(formatter)
                        val outputFile = File(fileDir, "ec135-$currentTime.csv")
                        val resultMsg =
                            writeLicencePlateItemsToCsv(licencePlateRowItems, outputFile)
                        Toast.makeText(context, resultMsg, Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "Nothing to save", Toast.LENGTH_SHORT).show()
                    }
                }) {
                Text(text = "Export")
            }
            Button(onClick = {
                viewModel.deleteRowItems()
            }) {
                Text(text = "Delete all")
            }
        }

        if (licencePlateRowItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(18.dp)
                    .fillMaxSize(),
            ) {
                Text(text = "Licence plates will be automatically saved here.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 6.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(licencePlateRowItems.asReversed()) { item ->
                    LicencePlateRow(item = item)
                }
            }
        }
    }
}