package by.alexandr7035.data.model.network.credentials.signed_vc

import by.alexandr7035.data.model.SignedCredential
import com.google.gson.annotations.SerializedName

data class SignVcRes(
    @SerializedName("signedCredential")
    val signedCredential: SignedCredential
)