package by.alexandr7035.data.network

import by.alexandr7035.data.model.credentials.CredentialRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface CredentialsApiService {
    @GET("api/v1/wallet/credentials")
    suspend fun getAllCredentials(@Header("Authorization") accessToken: String): Response<List<CredentialRes>>
}