package by.alexandr7035.affinidi_id.data

import by.alexandr7035.affinidi_id.data.model.SignInRequest
import by.alexandr7035.affinidi_id.data.model.SignInResponse
import by.alexandr7035.affinidi_id.data.model.SignUpRequest
import by.alexandr7035.affinidi_id.data.model.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("/api/v1/users/signup")
    // Returns TOKEN for sign up confirmation
    suspend fun signUp(@Body body: SignUpRequest): Response<String>

    @POST("api/v1/users/login")
    suspend fun signIn(@Body body: SignInRequest): Response<SignInResponse>

    @POST("api/v1/users/logout")
    suspend fun logOut(@Header("Authorization") accessToken: String): Response<Unit>
}