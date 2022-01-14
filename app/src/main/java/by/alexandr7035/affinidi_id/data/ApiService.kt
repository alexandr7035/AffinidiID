package by.alexandr7035.affinidi_id.data

import by.alexandr7035.affinidi_id.data.model.change_password.ChangePasswordRequest
import by.alexandr7035.affinidi_id.data.model.change_username.ChangeUserNameRequest
import by.alexandr7035.affinidi_id.data.model.change_username.ConfirmChangeUserNameRequest
import by.alexandr7035.affinidi_id.data.model.reset_password.ConfirmResetPasswordRequest
import by.alexandr7035.affinidi_id.data.model.reset_password.InitializeResetPasswordRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    // This request doesn't return anything but sends OTP to user's email
    @POST("api/v1/users/forgot-password")
    suspend fun initializePasswordReset(@Body body: InitializeResetPasswordRequest): Response<Unit>

    // This request doesn't return anything. 204 code for success
    @POST("api/v1/users/forgot-password/confirm")
    suspend fun confirmPasswordReset(@Body body: ConfirmResetPasswordRequest): Response<Unit>

    // 204 for success
    @POST("/api/v1/users/change-password")
    suspend fun changePassword(
        @Header("Authorization") accessToken: String,
        @Body body: ChangePasswordRequest
    ): Response<Unit>

    // 204 for success
    @POST("api/v1/users/change-username")
    suspend fun changeUserName(
        @Header("Authorization") accessToken: String,
        @Body body: ChangeUserNameRequest
    ): Response<Unit>

    // 204 for success
    @POST("api/v1/users/change-username/confirm")
    suspend fun confirmChangeUserName(
        @Header("Authorization") accessToken: String,
        @Body body: ConfirmChangeUserNameRequest
    ): Response<Unit>
}