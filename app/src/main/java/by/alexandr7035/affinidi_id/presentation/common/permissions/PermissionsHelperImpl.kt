package by.alexandr7035.affinidi_id.presentation.common.permissions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import javax.inject.Inject

class PermissionsHelperImpl @Inject constructor(private val permissionsPreferences: PermissionsPreferences) : PermissionsHelper {
    override fun checkForPermission(permission: String, activity: Activity): PermissionsCheckResult {

        // Permission granted
        if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
            return PermissionsCheckResult.PERMISSION_GRANTED
        }
        // Permission not granted
        else {
            // shouldShowRequestPermissionRationale() is FALSE in 2 cases:
            // Need to use persistent storage to distinguish the cases
            return if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                // TRUE the user has denied the permission previously but
                // has NOT checked the "Never Ask Again" checkbox.
                PermissionsCheckResult.SHOULD_REQUEST_PERMISSION
            } else {
                // User denied the permission previously AND selected "never ask again"
                if (permissionsPreferences.checkIfPermissionPreviouslyAsked(permission)) {
                    PermissionsCheckResult.SHOULD_REDIRECT_TO_SETTINGS
                }
                // When user is requesting permission for the first time
                else {
                    PermissionsCheckResult.SHOULD_REQUEST_PERMISSION
                }
            }
        }
    }

    override fun requestPermission(permission: String, activity: Activity) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), 0)
        permissionsPreferences.savePermissionAskedFlag(permission)
    }
}