package by.alexandr7035.affinidi_id.domain.repository

import by.alexandr7035.affinidi_id.domain.model.credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.unsigned_vc.BuildUnsignedVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.unsigned_vc.BuildUnsignedVcResModel
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel

interface CredentialsRepository {
    suspend fun getAllCredentials(authState: AuthStateModel): CredentialsListResModel

    suspend fun buildUnsignedVCObject(buildUnsignedVcReqModel: BuildUnsignedVcReqModel): BuildUnsignedVcResModel
}