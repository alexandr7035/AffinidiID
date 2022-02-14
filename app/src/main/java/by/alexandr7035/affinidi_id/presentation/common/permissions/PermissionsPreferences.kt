package by.alexandr7035.affinidi_id.presentation.common.permissions

interface PermissionsPreferences {
    fun savePermissionAskedFlag(permission: String)

    fun checkIfPermissionPreviouslyAsked(permission: String): Boolean
}