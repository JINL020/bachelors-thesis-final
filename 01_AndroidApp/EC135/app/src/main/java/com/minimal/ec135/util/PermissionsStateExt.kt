package com.minimal.ec135.util

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale

@ExperimentalPermissionsApi
fun PermissionStatus.isDenied(): Boolean {
    return shouldShowRationale || (!shouldShowRationale && !isGranted)
}