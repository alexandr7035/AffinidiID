package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeResModel
import by.alexandr7035.affinidi_id.domain.repository.VerificationRepository
import by.alexandr7035.data.datasource.cloud.ApiCallHelper
import by.alexandr7035.data.datasource.cloud.ApiCallWrapper
import by.alexandr7035.data.datasource.cloud.api.CredentialsApiService
import by.alexandr7035.data.helpers.vc_mapping.SignedCredentialToDomainMapper
import javax.inject.Inject

class VerificationRepositoryImpl @Inject constructor(
    private val credentialsApiService: CredentialsApiService,
    private val apiCallHelper: ApiCallHelper,
    private val mapper: SignedCredentialToDomainMapper
) : VerificationRepository {
    override suspend fun obtainCredentialFromQrCode(obtainVcFromQrCodeReq: ObtainVcFromQrCodeReqModel): ObtainVcFromQrCodeResModel {

        val res = apiCallHelper.executeCall {
            credentialsApiService.obtainVCFromQRCodeUrl(obtainVcFromQrCodeReq.credentialLink)
        }

        return when (res) {
            is ApiCallWrapper.Success -> {
                val domainCredential = mapper.map(res.data)
                ObtainVcFromQrCodeResModel.Success(credential = domainCredential)
            }
            // Return this error as any connection error may happen because of bad QR scanned (for example, not url)
            is ApiCallWrapper.HttpError -> ObtainVcFromQrCodeResModel.Fail(ErrorType.CREDENTIAL_QR_SCAN_ERROR)
            is ApiCallWrapper.Fail -> ObtainVcFromQrCodeResModel.Fail(ErrorType.CREDENTIAL_QR_SCAN_ERROR)
        }
    }
}