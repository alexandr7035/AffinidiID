package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcResModel
import by.alexandr7035.affinidi_id.domain.repository.VerificationRepository
import by.alexandr7035.data.datasource.cloud.ApiCallHelper
import by.alexandr7035.data.datasource.cloud.ApiCallWrapper
import by.alexandr7035.data.datasource.cloud.api.CredentialsApiService
import by.alexandr7035.data.helpers.vc_mapping.SignedCredentialToDomainMapper
import by.alexandr7035.data.model.network.credentials.verify_vcs.VerifyVCsReq
import com.google.gson.Gson
import com.google.gson.JsonObject
import javax.inject.Inject

class VerificationRepositoryImpl @Inject constructor(
    private val credentialsApiService: CredentialsApiService,
    private val apiCallHelper: ApiCallHelper,
    private val mapper: SignedCredentialToDomainMapper,
    private val gson: Gson
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



    override suspend fun verifyCredential(verifyVcReqModel: VerifyVcReqModel): VerifyVcResModel {
        // Convert to json
        // TODO review
        val json = gson.fromJson(verifyVcReqModel.rawVc, JsonObject::class.java)

        val res = apiCallHelper.executeCall {
            // Verify single VC
            credentialsApiService.verifyVCs(VerifyVCsReq(credentials = listOf(json)))
        }

        return when (res) {
            is ApiCallWrapper.Success -> VerifyVcResModel.Success(isValid = res.data.isValid)
            is ApiCallWrapper.HttpError -> {
                // This request returns 200 always
                // but handle error just in case for bad requests
                VerifyVcResModel.Fail(ErrorType.UNKNOWN_ERROR)
            }
            is ApiCallWrapper.Fail -> VerifyVcResModel.Fail(res.errorType)
        }
    }
}