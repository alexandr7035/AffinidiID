package by.alexandr7035.affinidi_id.data.implementation

import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.data.ApiService
import by.alexandr7035.affinidi_id.data.AuthRepository
import by.alexandr7035.affinidi_id.data.SignUpModel
import by.alexandr7035.affinidi_id.data.model.SignInRequest
import by.alexandr7035.affinidi_id.data.model.SignInModel
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val apiService: ApiService): AuthRepository {
    override suspend fun signUp(userName: String, password: String): SignUpModel {

        // FIXME test only
        return SignUpModel.Fail(ErrorType.USER_ALREADY_EXISTS)
    }

    override suspend fun signIn(userName: String, password: String): SignInModel {
//        return SignInResponse.Fail(ErrorType.USER_DOES_NOT_EXIST)
//        return try {
        val res = apiService.signIn(SignInRequest(userName, password))
        return SignInModel.Success(res.accessToken, res.userDid)
//        } catch (e: Exception) {
//            // FIXME map exceptions to errortypes
//            SignInResponse.Fail(ErrorType.UNKNOWN_ERROR)
//        }
    }
}