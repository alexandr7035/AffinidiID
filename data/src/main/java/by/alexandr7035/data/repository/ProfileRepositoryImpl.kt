package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.model.profile.UserProfile
import by.alexandr7035.affinidi_id.domain.repository.AppSettings
import by.alexandr7035.affinidi_id.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val appSettings: AppSettings
//    private val apiService: ApiService
): ProfileRepository {
    override fun getProfile(): UserProfile {
        return appSettings.getProfile()
    }
}