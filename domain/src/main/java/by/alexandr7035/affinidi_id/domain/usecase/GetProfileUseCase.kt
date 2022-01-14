package by.alexandr7035.affinidi_id.domain.usecase

import by.alexandr7035.affinidi_id.domain.model.profile.UserProfile
import by.alexandr7035.affinidi_id.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    fun execute(): UserProfile {
        return profileRepository.getProfile()
    }
}