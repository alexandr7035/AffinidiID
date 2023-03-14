package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.core.GenericRes
import by.alexandr7035.affinidi_id.domain.model.login.AuthCredentials
import by.alexandr7035.affinidi_id.domain.model.login.LogOutModel
import by.alexandr7035.affinidi_id.domain.repository.AppSettings
import by.alexandr7035.affinidi_id.domain.repository.LoginRepository
import by.alexandr7035.data.datasource.cache.credentials.CredentialsCacheDataSource
import by.alexandr7035.data.datasource.cache.secrets.SecretsStorage
import by.alexandr7035.data.datasource.cloud.ApiCallHelper
import by.alexandr7035.data.datasource.cloud.ApiCallWrapper
import by.alexandr7035.data.datasource.cloud.api.UserApiService
import by.alexandr7035.data.model.network.sign_in.SignInRequest
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val apiCallHelper: ApiCallHelper,
    private val secretsStorage: SecretsStorage,
    private val credentialsCacheDataSource: CredentialsCacheDataSource,
    private val appSettings: AppSettings
) : LoginRepository {

    override suspend fun signIn(userName: String, password: String): GenericRes<Unit> {
        val res = apiCallHelper.executeCall {
            userApiService.signIn(SignInRequest(userName, password))
        }

        return when (res) {
            is ApiCallWrapper.Success -> {
                // Save auth data
                appSettings.setAuthCredentials(
                    AuthCredentials(
                        userDid = res.data.userDid,
                        userEmail = userName,
                        accessToken = res.data.accessToken,
                        refreshToken = res.data.refreshToken,
                        accessTokenObtained = System.currentTimeMillis()
                    )
                )

                return GenericRes.Success(Unit)
            }
            is ApiCallWrapper.Fail -> {
                GenericRes.Fail(res.errorType)
            }
            is ApiCallWrapper.HttpError -> {
                when (res.resultCode) {
                    400 -> GenericRes.Fail(ErrorType.WRONG_USERNAME_OR_PASSWORD)
                    404 -> GenericRes.Fail(ErrorType.USER_DOES_NOT_EXIST)
                    else -> GenericRes.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }
        }
    }

    override suspend fun signInWithRefreshToken(accessToken: String): GenericRes<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun logOut(accessToken: String): LogOutModel {
        val res = apiCallHelper.executeCall {
            userApiService.logOut(accessToken)
        }

        return when (res) {
            is ApiCallWrapper.Success -> {
                secretsStorage.saveAccessToken(null)
                credentialsCacheDataSource.clearCredentialsCache()
                LogOutModel.Success
            }
            is ApiCallWrapper.HttpError -> {
                when (res.resultCode) {
                    401 -> LogOutModel.Fail(ErrorType.AUTHORIZATION_ERROR)
                    else -> LogOutModel.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }
            is ApiCallWrapper.Fail -> LogOutModel.Fail(res.errorType)
        }
    }
}