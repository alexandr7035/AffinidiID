package by.alexandr7035.affinidi_id.presentation.common.auth

import androidx.lifecycle.LiveData
import by.alexandr7035.affinidi_id.domain.model.auth_check.AuthCheckResModel

interface AuthController {
    fun checkIfPreviouslyAuthorized(): Boolean

    fun checkIfAuthorized()

    fun getAuthCheckObservable(): LiveData<AuthCheckResModel>

    fun checkAppLockedWithBiometrics(): Boolean

    fun logOut()

    fun getLogOutObservable(): LiveData<Unit>
}