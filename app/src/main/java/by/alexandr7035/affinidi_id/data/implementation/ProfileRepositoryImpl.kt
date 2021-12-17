package by.alexandr7035.affinidi_id.data.implementation

import by.alexandr7035.affinidi_id.core.AppError
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.data.*
import by.alexandr7035.affinidi_id.data.model.LogOutModel
import by.alexandr7035.affinidi_id.data.model.UserProfile
import timber.log.Timber
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val authDataStorage: AuthDataStorage,
    private val avatarsHelper: DicebearAvatarsHelper,
    private val apiService: ApiService
) : ProfileRepository {
    override fun getProfile(): UserProfile {
        return UserProfile(
            userName = authDataStorage.getUserName() ?: "Unknown username",
            userDid = authDataStorage.getDid() ?: "Unknown DID"
        )
    }

    override fun getProfileImageUrl(userDid: String): String {

        // Did contains ":" symbol which can't be used in urls
        // So split did and get last part
        // Also take last 25 symbols as elem DID may be extremely long
        val uniqueString = userDid.split(":").last().takeLast(25)

        return avatarsHelper.getImageUrl(
            imageType = DicebearImageType.AVATAR_IDENTICON,
            dataString = uniqueString,
            imageSize = 1
        )
    }

    override suspend fun logOut(): LogOutModel {
        try {
            val res = apiService.logOut(authDataStorage.getAccessToken() ?: "")

            if (res.isSuccessful) {
                Timber.debug("LOGOUT SUCCESSFUL")

                authDataStorage.saveUserName(null)
                authDataStorage.saveAccessToken(null)
                authDataStorage.saveDid(null)

                return LogOutModel.Success()
            }
            else {
                return when (res.code()) {
                    401 -> {
                        // Authorization token already unactual
                        // Just clear it and return success logout
                        authDataStorage.saveUserName(null)
                        authDataStorage.saveAccessToken(null)
                        authDataStorage.saveDid(null)
                        return LogOutModel.Success()
                    }
                    else -> {
                        LogOutModel.Fail(ErrorType.UNKNOWN_ERROR)
                    }
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            appError.printStackTrace()
            Timber.debug("ERRORTYPE ${appError.errorType.name}")
            return LogOutModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            Timber.debug("LOGOUT FAILED other exception $e")
            e.printStackTrace()
            return LogOutModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }
}