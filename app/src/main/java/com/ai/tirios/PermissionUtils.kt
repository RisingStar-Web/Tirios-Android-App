package com.ai.tirios

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.preference.PreferenceManager
import android.provider.Settings
import androidx.fragment.app.Fragment

object PermissionUtils {

    private fun useRunTimePermissions() = Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1

    private fun hasPermission(activity: Activity, permission: String) =
        if (useRunTimePermissions()) activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED else true

    fun requestPermissions(activity: Activity, permission: Array<String>, requestCode: Int) {
        if (useRunTimePermissions()) activity.requestPermissions(permission, requestCode)
    }

    fun requestPermissions(fragment: Fragment, permission: Array<String>, requestCode: Int) {
        if (useRunTimePermissions()) fragment.requestPermissions(permission, requestCode)
    }

    private fun shouldShowRational(activity: Activity, permission: String) =
        if (useRunTimePermissions()) activity.shouldShowRequestPermissionRationale(permission) else false

    fun shouldAskForPermission(activity: Activity, permission: String) =
        if (useRunTimePermissions()) !hasPermission(
            activity,
            permission
        ) && (!hasAskedForPermission(activity, permission) || shouldShowRational(
            activity,
            permission
        )) else false

    fun goToAppSettings(activity: Activity) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", activity.packageName, null)
        ).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        activity.startActivity(intent)
    }

    private fun hasAskedForPermission(activity: Activity, permission: String) =
        PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(permission, false)

    fun markedPermissionAsAsked(activity: Activity, permission: String) =
        PreferenceManager
            .getDefaultSharedPreferences(activity)
            .edit()
            .putBoolean(permission, true)
            .apply()
}
