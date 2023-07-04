package by.alexandr7035.affinidi_id.presentation.common.auth

import androidx.lifecycle.LiveData
import by.alexandr7035.affinidi_id.domain.core.GenericRes

interface AuthController {
    fun checkIfAuthorized()

    fun getAuthCheckObservable(): LiveData<GenericRes<Unit>>

    fun checkAppLockedWithBiometrics(): Boolean

    fun logOut()

    fun getLogOutObservable(): LiveData<Unit>
}