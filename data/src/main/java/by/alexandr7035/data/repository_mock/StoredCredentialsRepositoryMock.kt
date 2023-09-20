package by.alexandr7035.data.repository_mock

import by.alexandr7035.affinidi_id.domain.model.credentials.check_if_have_vc.CheckIfHaveVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.check_if_have_vc.CheckIfHaveVcResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdResModel
import by.alexandr7035.affinidi_id.domain.repository.StoredCredentialsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class StoredCredentialsRepositoryMock: StoredCredentialsRepository {
    override suspend fun getAllCredentials(): Flow<CredentialsListResModel> {
        return flow {
            emit(CredentialsListResModel.Loading)
            emit(CredentialsListResModel.Success(MockConstants.CREDENTIALS))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun deleteCredential(deleteVcReqModel: DeleteVcReqModel): DeleteVcResModel {
        return DeleteVcResModel.Success
    }

    override suspend fun getCredentialById(getCredentialByIdReqModel: GetCredentialByIdReqModel): Flow<GetCredentialByIdResModel> {
        return flow {
            emit(GetCredentialByIdResModel.Loading)

            val vc = MockConstants.CREDENTIALS.findLast {
                it.id == getCredentialByIdReqModel.credentialId
            }

            vc?.let {
                emit(GetCredentialByIdResModel.Success(it))
            }

        }.flowOn(Dispatchers.IO)
    }

    override suspend fun shareCredential(shareVcReq: ShareCredentialReqModel): ShareCredentialResModel {
        delay(MockConstants.MOCK_REQ_DELAY_MILLS)
        return ShareCredentialResModel.Success(base64QrCode = MockConstants.MOCK_QR_CODE)
    }

    override suspend fun checkIfHaveCredentialInCache(checkIfHaveVcReqModel: CheckIfHaveVcReqModel): CheckIfHaveVcResModel {
        return CheckIfHaveVcResModel(haveCredential = true)
    }
}