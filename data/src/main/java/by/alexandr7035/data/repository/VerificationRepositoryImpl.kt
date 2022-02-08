package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeResModel
import by.alexandr7035.affinidi_id.domain.repository.VerificationRepository
import by.alexandr7035.data.core.AppError
import by.alexandr7035.data.datasource.cloud.api.CredentialsApiService
import by.alexandr7035.data.helpers.vc_mapping.SignedCredentialToDomainMapper
import by.alexandr7035.data.model.SignedCredential
import javax.inject.Inject

class VerificationRepositoryImpl @Inject constructor(
    private val credentialsApiService: CredentialsApiService,
    private val mapper: SignedCredentialToDomainMapper
) : VerificationRepository {
    override suspend fun obtainCredentialFromQrCode(obtainVcFromQrCodeReq: ObtainVcFromQrCodeReqModel): ObtainVcFromQrCodeResModel {
        try {
            val res = credentialsApiService.obtainVCFromQRCodeUrl(obtainVcFromQrCodeReq.credentialLink)

            return if (res.isSuccessful) {
                val signedVc = res.body() as SignedCredential
                val domainVc = mapper.map(signedVc)
                ObtainVcFromQrCodeResModel.Success(credential = domainVc)
            } else {
                ObtainVcFromQrCodeResModel.Fail(ErrorType.CREDENTIAL_QR_SCAN_ERROR)
            }
        }
        catch (appError: AppError) {
            return ObtainVcFromQrCodeResModel.Fail(ErrorType.CREDENTIAL_QR_SCAN_ERROR)
        }
        // Unknown exception
        catch (e: Exception) {
            e.printStackTrace()
            return ObtainVcFromQrCodeResModel.Fail(ErrorType.CREDENTIAL_QR_SCAN_ERROR)
        }
    }
}