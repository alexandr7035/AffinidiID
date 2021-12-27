package by.alexandr7035.affinidi_id.data

import by.alexandr7035.affinidi_id.data.model.reset_password.ConfirmResetPasswordRequest
import by.alexandr7035.affinidi_id.data.model.reset_password.InitializeResetPasswordRequest
import by.alexandr7035.affinidi_id.data.model.sign_in.SignInRequest
import by.alexandr7035.affinidi_id.data.model.sign_in.SignInResponse
import by.alexandr7035.affinidi_id.data.model.sign_up.ConfirmSignUpRequest
import by.alexandr7035.affinidi_id.data.model.sign_up.ConfirmSignUpResponse
import by.alexandr7035.affinidi_id.data.model.sign_up.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("/api/v1/users/signup")
    // Returns TOKEN for sign up confirmation
    suspend fun signUp(@Body body: SignUpRequest): Response<String>

    @POST("/api/v1/users/signup/confirm")
    suspend fun confirmSignUp(
        @Body body: ConfirmSignUpRequest
    ): Response<ConfirmSignUpResponse>

    @POST("api/v1/users/login")
    suspend fun signIn(@Body body: SignInRequest): Response<SignInResponse>

    @POST("api/v1/users/logout")
    suspend fun logOut(@Header("Authorization") accessToken: String): Response<Unit>

    // This request doesn't return anything but sends OTP to user's email
    @POST("api/v1/users/forgot-password")
    suspend fun initializePasswordReset(@Body body: InitializeResetPasswordRequest): Response<Unit>

    // This request doesn't return anything. 204 code for success
    @POST("api/v1/users/forgot-password/confirm")
    suspend fun confirmPasswordReset(@Body body: ConfirmResetPasswordRequest): Response<Unit>
}