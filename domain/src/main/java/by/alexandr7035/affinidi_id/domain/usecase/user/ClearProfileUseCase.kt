package by.alexandr7035.affinidi_id.domain.usecase.user

import by.alexandr7035.affinidi_id.domain.repository.ProfileRepository
import javax.inject.Inject

class ClearProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    fun execute() {
        profileRepository.clearProfile()
    }
}