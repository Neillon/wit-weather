package br.com.neillon.core.androidextensions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

fun Context.checkPermissions(vararg permissions: String): Boolean {
    var hasAllPermissions = true

    permissions.forEach { permission ->
        val hasThisPermission = ActivityCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
        if (!hasThisPermission) {
            hasAllPermissions = false
            return@forEach
        }
    }

    return hasAllPermissions
}