package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.login.LogOutModel
import by.alexandr7035.affinidi_id.domain.model.login.SignInModel
import by.alexandr7035.affinidi_id.domain.repository.LoginRepository
import by.alexandr7035.data.datasource.cache.credentials.CredentialsCacheDataSource
import by.alexandr7035.data.datasource.cache.credentials.CredentialsDAO
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
    private val credentialsCacheDataSource: CredentialsCacheDataSource
) : LoginRepository {

    override suspend fun signIn(userName: String, password: String): SignInModel {

        val res = apiCallHelper.executeCall {
            userApiService.signIn(SignInRequest(userName, password))
        }

        return when (res) {
            is ApiCallWrapper.Success -> {
                secretsStorage.saveAccessToken(res.data.accessToken)
                return SignInModel.Success(userDid = res.data.userDid, userName = userName)
            }
            is ApiCallWrapper.Fail -> SignInModel.Fail(res.errorType)
            is ApiCallWrapper.HttpError -> {
                when (res.resultCode) {
                    400 -> SignInModel.Fail(ErrorType.WRONG_USERNAME_OR_PASSWORD)
                    404 -> SignInModel.Fail(ErrorType.USER_DOES_NOT_EXIST)
                    else -> SignInModel.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }
        }
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

    override fun saveAccessToken(accessToken: String?) {
        secretsStorage.saveAccessToken(accessToken)
    }

    override fun getAccessToken(): String? {
        return secretsStorage.getAccessToken()
    }
}