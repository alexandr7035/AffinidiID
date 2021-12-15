package by.alexandr7035.affinidi_id.data.implementation

import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.data.AuthRepository
import by.alexandr7035.affinidi_id.data.SignUpModel
import by.alexandr7035.affinidi_id.data.model.SignInModel

class AuthRepositoryImpl: AuthRepository {
    override suspend fun signUp(userName: String, password: String): SignUpModel {

        // FIXME test only
        return SignUpModel.Fail(ErrorType.USER_ALREADY_EXISTS)
    }

    override fun signIn(userName: String, password: String): SignInModel {
        return SignInModel.Fail(ErrorType.USER_DOES_NOT_EXIST)
    }
}