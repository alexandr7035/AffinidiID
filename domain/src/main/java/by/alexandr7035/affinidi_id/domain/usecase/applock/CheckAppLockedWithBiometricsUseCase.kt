package by.alexandr7035.affinidi_id.domain.usecase.applock

import by.alexandr7035.affinidi_id.domain.repository.AppSettings
import javax.inject.Inject

class CheckAppLockedWithBiometricsUseCase @Inject constructor(
    private val appSettings: AppSettings
) {
    fun execute(): Boolean {
        return appSettings.isAppLockedWithBiometrics()
    }
}