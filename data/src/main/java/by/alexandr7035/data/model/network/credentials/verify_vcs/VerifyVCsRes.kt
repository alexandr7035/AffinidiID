package by.alexandr7035.data.model.network.credentials.verify_vcs

import com.google.gson.annotations.SerializedName

data class VerifyVCsRes(
    @SerializedName("isValid")
    val isValid: Boolean
)