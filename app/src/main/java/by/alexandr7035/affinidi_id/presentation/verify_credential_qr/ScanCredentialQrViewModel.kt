package by.alexandr7035.affinidi_id.presentation.verify_credential_qr

import android.Manifest
import android.app.Activity
import androidx.lifecycle.ViewModel
import by.alexandr7035.affinidi_id.presentation.common.permissions.PermissionsCheckResult
import by.alexandr7035.affinidi_id.presentation.common.permissions.PermissionsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScanCredentialQrViewModel @Inject constructor(
    private val permissionsHelper: PermissionsHelper
): ViewModel() {
    fun askForCameraPermissions(activity: Activity): PermissionsCheckResult {
        return permissionsHelper.checkForPermission(Manifest.permission.CAMERA, activity)
    }

    fun requestCameraPermissions(activity: Activity) {
        permissionsHelper.requestPermission(Manifest.permission.CAMERA, activity)
    }
}