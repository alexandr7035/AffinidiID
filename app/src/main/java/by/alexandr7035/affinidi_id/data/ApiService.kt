package by.alexandr7035.affinidi_id.data

import by.alexandr7035.affinidi_id.data.model.change_password.ChangePasswordRequest
import by.alexandr7035.affinidi_id.data.model.change_username.ChangeUserNameRequest
import by.alexandr7035.affinidi_id.data.model.change_username.ConfirmChangeUserNameRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

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