package by.alexandr7035.affinidi_id.domain.usecase.applock

import by.alexandr7035.affinidi_id.domain.repository.AppSettings
import javax.inject.Inject

class SetAppLockedWithBiometricsUseCase @Inject constructor(
    private val appSettings: AppSettings
) {
    fun execute(locked: Boolean) {
        return appSettings.setAppLockedWithBiometrics(locked)
    }
}