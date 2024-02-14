package com.minimal.ec135.screenLicence

import android.util.Log
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

@Composable
fun LicencePlatesScreen() {
    val TAG = "LicencePlatesScreen"
    Log.d(TAG, "Init LicencePlatesScreen")

    val viewModel = viewModel<MainViewModel>((LocalContext.current as ComponentActivity))
    val licencePlateRowItems by viewModel.licencePlateRowItems.collectAsState()
    //val licencePlateRowItems = viewModel.licencePlateRowItems

    Column {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Saved Licence Plates: ${licencePlateRowItems.size}"
            )
            Button(onClick = {
                viewModel.deleteRowItems()
            }) {
                Text(text = "Delete all")
            }
        }

        if (licencePlateRowItems.isEmpty()) {
            Box(
                modifier = Modifier.padding(18.dp).fillMaxSize(),
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