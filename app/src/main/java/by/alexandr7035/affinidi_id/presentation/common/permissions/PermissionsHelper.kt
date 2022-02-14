package by.alexandr7035.affinidi_id.presentation.common.permissions

import android.app.Activity

interface PermissionsHelper {
    fun checkForPermission(permission: String, activity: Activity): PermissionsCheckResult

    fun requestPermission(permission: String, activity: Activity)
}