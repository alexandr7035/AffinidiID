package by.alexandr7035.data.model.network.credentials.store_vc

import com.google.gson.annotations.SerializedName

data class StoreVCsRes(
    @SerializedName("credentialIds")
    val storedVCIDs: List<String>
)