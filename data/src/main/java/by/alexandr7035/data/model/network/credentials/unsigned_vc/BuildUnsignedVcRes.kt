package by.alexandr7035.data.model.network.credentials.unsigned_vc

import com.google.gson.annotations.SerializedName

data class BuildUnsignedVcRes(
    @SerializedName("unsignedCredential")
    val unsignedCredential: UnsignedCredential
)