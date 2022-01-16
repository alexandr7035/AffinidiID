package by.alexandr7035.data.model.credentials.signed_vc

import com.google.gson.annotations.SerializedName

data class SignVcRes(
    @SerializedName("signedCredential")
    val signedCredential: SignedCredential
)