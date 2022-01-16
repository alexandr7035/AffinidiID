package by.alexandr7035.data.network

import by.alexandr7035.data.model.credentials.signed_vc.SignedCredential
import by.alexandr7035.data.model.credentials.unsigned_vc.BuildUnsignedVcReq
import by.alexandr7035.data.model.credentials.unsigned_vc.BuildUnsignedVcRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CredentialsApiService {
    @GET("api/v1/wallet/credentials")
    suspend fun getAllCredentials(@Header("Authorization") accessToken: String): Response<List<SignedCredential>>

    @POST("https://affinity-issuer.prod.affinity-project.org/api/v1/vc/build-unsigned")
    suspend fun buildUnsignedVCObject(@Body body: BuildUnsignedVcReq): Response<BuildUnsignedVcRes>
}