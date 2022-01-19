package by.alexandr7035.data.network

import by.alexandr7035.data.model.credentials.signed_vc.SignVcReq
import by.alexandr7035.data.model.credentials.signed_vc.SignVcRes
import by.alexandr7035.data.model.credentials.signed_vc.SignedCredential
import by.alexandr7035.data.model.credentials.store_vc.StoreVCsReq
import by.alexandr7035.data.model.credentials.store_vc.StoreVCsRes
import by.alexandr7035.data.model.credentials.unsigned_vc.BuildUnsignedVcReq
import by.alexandr7035.data.model.credentials.unsigned_vc.BuildUnsignedVcRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CredentialsApiService {
    @GET("${WALLET_API_BASE_URL}/api/v1/wallet/credentials")
    suspend fun getAllCredentials(@Header("Authorization") accessToken: String): Response<List<SignedCredential>>

    @POST("${ISSUER_API_BASE_URL}/api/v1/vc/build-unsigned")
    suspend fun buildUnsignedVCObject(@Body body: BuildUnsignedVcReq): Response<BuildUnsignedVcRes>

    @POST("${WALLET_API_BASE_URL}/api/v1/wallet/sign-credential")
    suspend fun signVC(@Body body: SignVcReq, @Header("Authorization") accessToken: String): Response<SignVcRes>

    @POST("${WALLET_API_BASE_URL}/api/v1/wallet/credentials")
    suspend fun storeVCs(@Body body: StoreVCsReq, @Header("Authorization") accessToken: String): Response<StoreVCsRes>

    companion object {
        // An annotation argument must be a compile-time constant
        private const val WALLET_API_BASE_URL = "https://cloud-wallet-api.prod.affinity-project.org"
        private const val ISSUER_API_BASE_URL = "https://affinity-issuer.prod.affinity-project.org"
    }
}