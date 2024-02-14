package com.minimal.ec135

import androidx.camera.core.CameraSelector
import androidx.lifecycle.ViewModel
import com.minimal.ec135.screenLicence.LicencePlateRowItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val TAG = "MainViewModel"

    private val _cameraSelector = MutableStateFlow(CameraSelector.DEFAULT_BACK_CAMERA)
    var cameraSelector: CameraSelector
        get() = _cameraSelector.value
        set(value) {
            _cameraSelector.value = value
        }

    private val _inputString = MutableStateFlow("")
    var inputString: String
        get() = _inputString.value
        set(value) {
            _inputString.value = value
        }

    private val _licencePlateRowItems = MutableStateFlow<List<LicencePlateRowItem>>(emptyList())
    val licencePlateRowItems = _licencePlateRowItems.asStateFlow()

    fun addRowItem(item: LicencePlateRowItem) {
        _licencePlateRowItems.value += item
    }
    fun deleteRowItems() {
        _licencePlateRowItems.value = emptyList()
    }

    private var _objectDetectionThreshold = 0.7f
    var objectDetectionThreshold: Float
        get() = _objectDetectionThreshold
        set(value) {
            _objectDetectionThreshold = value
        }

    private var _ocrThreshold = 0f
    var ocrThreshold: Float
        get() = _ocrThreshold
        set(value) {
            _ocrThreshold = value
        }

    private var _similarityThreshold = 1f
    var similarityThreshold: Float
        get() = _similarityThreshold
        set(value) {
            _similarityThreshold = value
        }
}