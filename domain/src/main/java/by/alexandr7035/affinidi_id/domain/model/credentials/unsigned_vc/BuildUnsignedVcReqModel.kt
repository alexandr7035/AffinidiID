package by.alexandr7035.affinidi_id.domain.model.credentials.unsigned_vc

class BuildUnsignedVcReqModel(
    val holderDid: String,
    val expiresAt: Long?,
    val buildCredentialType: BuildCredentialType,
)