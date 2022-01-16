package by.alexandr7035.data.model.credentials.unsigned_vc

import com.google.gson.annotations.SerializedName

data class BuildUnsignedVcRes(
    @SerializedName("unsignedCredential")
    val unsignedCredential: UnsignedCredential
)