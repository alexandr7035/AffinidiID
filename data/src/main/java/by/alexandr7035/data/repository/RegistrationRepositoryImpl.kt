package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.signup.ConfirmSignUpRequestModel
import by.alexandr7035.affinidi_id.domain.model.signup.ConfirmSignUpResponseModel
import by.alexandr7035.affinidi_id.domain.model.signup.SignUpRequestModel
import by.alexandr7035.affinidi_id.domain.model.signup.SignUpResponseModel
import by.alexandr7035.affinidi_id.domain.repository.RegistrationRepository
import by.alexandr7035.data.datasource.cache.secrets.SecretsStorage
import by.alexandr7035.data.datasource.cloud.ApiCallHelper
import by.alexandr7035.data.datasource.cloud.ApiCallWrapper
import by.alexandr7035.data.datasource.cloud.api.UserApiService
import by.alexandr7035.data.model.network.sign_up.ConfirmSignUpRequest
import by.alexandr7035.data.model.network.sign_up.SignUpMessageParams
import by.alexandr7035.data.model.network.sign_up.SignUpOptions
import by.alexandr7035.data.model.network.sign_up.SignUpRequest
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val apiService: UserApiService,
    private val apiCallHelper: ApiCallHelper,
    private val secretsStorage: SecretsStorage
) : RegistrationRepository {

    override suspend fun signUp(signUpRequestModel: SignUpRequestModel): SignUpResponseModel {
        val signUpRequest = SignUpRequest(
            userName = signUpRequestModel.userName,
            password = signUpRequestModel.password,
            // Use with default params
            signUpOptions = SignUpOptions(),
            signUpMessageParams = SignUpMessageParams()
        )

        val res = apiCallHelper.executeCall {
            apiService.signUp(signUpRequest)
        }

        return when (res) {
            is ApiCallWrapper.Success -> {
                SignUpResponseModel.Success(res.data)
            }

            is ApiCallWrapper.HttpError -> {
                when (res.resultCode) {
                    409 -> SignUpResponseModel.Fail(ErrorType.USER_ALREADY_EXISTS)
                    else -> SignUpResponseModel.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }
            is ApiCallWrapper.Fail -> SignUpResponseModel.Fail(res.errorType)
        }
    }


    override suspend fun confirmSignUp(confirmSignUpRequestModel: ConfirmSignUpRequestModel): ConfirmSignUpResponseModel {

        val requestBody = ConfirmSignUpRequest(
            token = confirmSignUpRequestModel.confirmationToken,
            confirmationCode = confirmSignUpRequestModel.confirmationCode
        )

        val res = apiCallHelper.executeCall {
            apiService.confirmSignUp(requestBody)
        }

        return when (res) {
            is ApiCallWrapper.Success -> {
                secretsStorage.saveAccessToken(res.data.accessToken)
                ConfirmSignUpResponseModel.Success(
                    accessToken = res.data.accessToken,
                    userDid = res.data.userDid
                )
            }
            is ApiCallWrapper.HttpError -> {
                when (res.resultCode) {
                    400 -> ConfirmSignUpResponseModel.Fail(ErrorType.WRONG_CONFIRMATION_CODE)
                    else -> ConfirmSignUpResponseModel.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }
            is ApiCallWrapper.Fail -> ConfirmSignUpResponseModel.Fail(res.errorType)
        }
    }
}