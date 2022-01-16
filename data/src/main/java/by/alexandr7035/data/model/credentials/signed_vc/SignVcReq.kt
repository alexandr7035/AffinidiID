package by.alexandr7035.data.model.credentials.signed_vc

import by.alexandr7035.data.model.credentials.unsigned_vc.UnsignedCredential
import com.google.gson.annotations.SerializedName

data class SignVcReq(
    @SerializedName("unsignedCredential")
    val unsignedCredential: UnsignedCredential
)