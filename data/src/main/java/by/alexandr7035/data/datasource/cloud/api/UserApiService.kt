package by.alexandr7035.data.datasource.cloud.api

import by.alexandr7035.data.model.network.EmptyResponse
import by.alexandr7035.data.model.network.profile.ChangePasswordRequest
import by.alexandr7035.data.model.network.reset_password.ConfirmResetPasswordRequest
import by.alexandr7035.data.model.network.reset_password.InitializeResetPasswordRequest
import by.alexandr7035.data.model.network.sign_in.RefreshTokenRequest
import by.alexandr7035.data.model.network.sign_in.RefreshTokenResponse
import by.alexandr7035.data.model.network.sign_in.SignInRequest
import by.alexandr7035.data.model.network.sign_in.SignInResponse
import by.alexandr7035.data.model.network.sign_up.ConfirmSignUpRequest
import by.alexandr7035.data.model.network.sign_up.ConfirmSignUpResponse
import by.alexandr7035.data.model.network.sign_up.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApiService {

    @POST("$WALLET_API_BASE_URL/api/v1/users/signup")
    // Returns TOKEN for sign up confirmation
    suspend fun signUp(@Body body: SignUpRequest): Response<String>

    @POST("$WALLET_API_BASE_URL/api/v1/users/signup/confirm")
    suspend fun confirmSignUp(
        @Body body: ConfirmSignUpRequest
    ): Response<ConfirmSignUpResponse>

    @POST("$WALLET_API_BASE_URL/api/v1/users/login")
    suspend fun signIn(@Body body: SignInRequest): Response<SignInResponse>

    @POST("$WALLET_API_BASE_URL/api/v1/users/login-with-refresh-token")
    suspend fun signInWithRefreshToken(@Body body: RefreshTokenRequest): Response<RefreshTokenResponse>

    @POST("$WALLET_API_BASE_URL/api/v1/users/logout")
    suspend fun logOut(@Header("Authorization") accessToken: String): Response<EmptyResponse>

    // This request doesn't return anything but sends OTP to user's email
    @POST("$WALLET_API_BASE_URL/api/v1/users/forgot-password")
    suspend fun initializePasswordReset(@Body body: InitializeResetPasswordRequest): Response<EmptyResponse>

    // This request doesn't return anything. 204 code for success
    @POST("$WALLET_API_BASE_URL/api/v1/users/forgot-password/confirm")
    suspend fun confirmPasswordReset(@Body body: ConfirmResetPasswordRequest): Response<EmptyResponse>

    // 204 for success
    @POST("$WALLET_API_BASE_URL/api/v1/users/change-password")
    suspend fun changePassword(
        @Header("Authorization") accessToken: String,
        @Body body: ChangePasswordRequest
    ): Response<EmptyResponse>


    @GET("$WALLET_API_BASE_URL/api/v1/users/get-did")
    suspend fun getUserDID(@Header("Authorization") accessToken: String): Response<String>

    companion object {
        // An annotation argument must be a compile-time constant
        private const val WALLET_API_BASE_URL = "https://cloud-wallet-api.prod.affinity-project.org"
    }
}