package by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc

class IssueCredentialReqModel(
    val holderDid: String,
    val expiresAt: Long?,
    val credentialType: CredentialType,
)