package by.alexandr7035.affinidi_id.domain.usecase

import by.alexandr7035.affinidi_id.domain.model.profile.SaveProfileModel
import by.alexandr7035.affinidi_id.domain.repository.ProfileRepository
import javax.inject.Inject

class SaveProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    fun execute(saveProfileModel: SaveProfileModel) {
        profileRepository.saveProfile(saveProfileModel)
    }
}