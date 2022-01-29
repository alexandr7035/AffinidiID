package by.alexandr7035.affinidi_id.domain.repository

import by.alexandr7035.affinidi_id.domain.model.auth_check.AuthCheckResModel
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel

interface AuthCheckRepository {
    suspend fun makeCheckRequest(authStateModel: AuthStateModel): AuthCheckResModel
}