package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.model.login.SignInModel
import by.alexandr7035.affinidi_id.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(): LoginRepository {
    override suspend fun signIn(userName: String, password: String): SignInModel {
        // FIXME
        return SignInModel.Success("token", "", "did")
    }
}