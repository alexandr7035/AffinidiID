package by.alexandr7035.data.datasource.cloud.api

import by.alexandr7035.data.model.network.credentials.signed_vc.SignVcReq
import by.alexandr7035.data.model.network.credentials.signed_vc.SignVcRes
import by.alexandr7035.data.model.SignedCredential
import by.alexandr7035.data.model.network.credentials.store_vc.StoreVCsReq
import by.alexandr7035.data.model.network.credentials.store_vc.StoreVCsRes
import by.alexandr7035.data.model.network.credentials.unsigned_vc.BuildUnsignedVcReq
import by.alexandr7035.data.model.network.credentials.unsigned_vc.BuildUnsignedVcRes
import by.alexandr7035.data.model.network.verify_vcs.VerifyVCsReq
import by.alexandr7035.data.model.network.verify_vcs.VerifyVCsRes
import retrofit2.Response
import retrofit2.http.*

interface CredentialsApiService {
    @GET("$WALLET_API_BASE_URL/api/v1/wallet/credentials")
    suspend fun getAllCredentials(@Header("Authorization") accessToken: String): Response<List<SignedCredential>>

    @POST("$ISSUER_API_BASE_URL/api/v1/vc/build-unsigned")
    suspend fun buildUnsignedVCObject(@Body body: BuildUnsignedVcReq): Response<BuildUnsignedVcRes>

    @POST("$WALLET_API_BASE_URL/api/v1/wallet/sign-credential")
    suspend fun signVC(@Body body: SignVcReq, @Header("Authorization") accessToken: String): Response<SignVcRes>

    @POST("$WALLET_API_BASE_URL/api/v1/wallet/credentials")
    suspend fun storeVCs(@Body body: StoreVCsReq, @Header("Authorization") accessToken: String): Response<StoreVCsRes>

    // No model, 200 for success
    @DELETE("$WALLET_API_BASE_URL/api/v1/wallet/credentials/{id}")
    suspend fun deleteVc(@Path("id") credentialId: String, @Header("Authorization") accessToken: String): Response<Unit>

    // Returns true if all the VCs are valid, false if at least one is not
    @POST("$VERIFIER_API_BASE_URL/api/v1/verifier/verify-vcs")
    suspend fun verifyVCs(@Body body: VerifyVCsReq): Response<VerifyVCsRes>

    companion object {
        // An annotation argument must be a compile-time constant
        private const val WALLET_API_BASE_URL = "https://cloud-wallet-api.prod.affinity-project.org"
        private const val ISSUER_API_BASE_URL = "https://affinity-issuer.prod.affinity-project.org"
        private const val VERIFIER_API_BASE_URL = "https://affinity-verifier.prod.affinity-project.org"
    }
}