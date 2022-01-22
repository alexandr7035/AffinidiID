package by.alexandr7035.data.model.network.credentials.store_vc

import by.alexandr7035.data.model.SignedCredential
import com.google.gson.annotations.SerializedName

data class StoreVCsReq(
    @SerializedName("data")
    val credentialsToStore: List<SignedCredential>
)