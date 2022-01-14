package by.alexandr7035.data.api

import by.alexandr7035.data.model.sign_in.SignInRequest
import by.alexandr7035.data.model.sign_in.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("api/v1/users/login")
    suspend fun signIn(@Body body: SignInRequest): Response<SignInResponse>

    @POST("api/v1/users/logout")
    suspend fun logOut(@Header("Authorization") accessToken: String): Response<Unit>
}