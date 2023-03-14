package by.alexandr7035.affinidi_id.domain.usecase.user

import by.alexandr7035.affinidi_id.domain.model.profile.UserProfile
import by.alexandr7035.affinidi_id.domain.repository.AppSettings
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val appSettings: AppSettings) {
    fun execute(): UserProfile {
        return appSettings.getProfile()
    }
}