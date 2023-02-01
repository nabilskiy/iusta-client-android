package com.ls.iusta.presentation.managers

import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PermissionManager @Inject constructor(@ApplicationContext val context: Context) {

    private var permissions = ArrayList<String>()

    private fun isNotGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) != PERMISSION_GRANTED
    }

    fun storagePermission(): PermissionManager {
        if (isNotGranted(WRITE_EXTERNAL_STORAGE)) addPermission(WRITE_EXTERNAL_STORAGE)
        if (isNotGranted(READ_EXTERNAL_STORAGE)) addPermission(READ_EXTERNAL_STORAGE)
        return this
    }

    fun cameraPermission(): PermissionManager {
        if (isNotGranted(CAMERA)) addPermission(CAMERA)
        return this
    }

    fun requestFor(activity: Activity): Short {
        if (permissions.size == 0) return PERMISSIONS_GRANTED
        ActivityCompat.requestPermissions(
            activity,
            permissions.toTypedArray(),
            100
        )
        return PERMISSIONS_SUCCESS
    }

    fun requestForFragment(fragment: Fragment): Short {
        if (permissions.size == 0) return PERMISSIONS_GRANTED
        fragment.requestPermissions(
            permissions.toTypedArray(),
            100
        )
        return PERMISSIONS_SUCCESS
    }


    fun checkAllGranted(grantResults: IntArray): Boolean {
        for (result in grantResults) {
            if (result != PERMISSION_GRANTED) return false
        }
        return true
    }

    private fun addPermission(permission: String) {
        if (permissions.contains(permission)) return
        permissions.add(permission)
    }

    companion object {
        const val PERMISSIONS_GRANTED: Short = 1
        const val PERMISSIONS_SUCCESS: Short = 0
    }
}