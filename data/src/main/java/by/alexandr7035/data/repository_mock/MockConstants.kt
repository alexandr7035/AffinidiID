package by.alexandr7035.data.repository_mock

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialProof
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus

object MockConstants {
    const val MOCK_LOGIN = "test@mail.com"
    const val MOCK_PASSWORD = "1234567Ab"
    const val MOCK_DID = "did:mock:1234567abcd"
    const val MOCK_REQ_DELAY_MILLS = 1000L

    const val MOCK_QR_CODE = "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"

    val CREDENTIALS = listOf(
        Credential(
            vcType = "Proof of E-mail",
            vcSchema = "http//exampleschemas.com/email.json",
            credentialStatus = CredentialStatus.Active,
            credentialSubjectData = "{\"email\":\"${MOCK_LOGIN}\"}",
            expirationDate = 1735689600000L,
            issuanceDate = 1672531200000L,
            issuerDid = "did:mock:issuer:1234567",
            id = "claimid:123",
            holderDid = MOCK_DID,
            credentialProof = CredentialProof(
                creationDate = 1672531200000L,
                proofPurpose = "assertionMethod",
                verificationMethod = "${MOCK_DID}#primary",
                jws = "JTrUe7GOobRsZf5w+djg4qaAWDdukc4dg+kog3CwoiA9vJqqgUAhrTkashDrvxaFksR6Ngx1Cib+RV9X1XC/HccfoBTMMdkSe9t/IVhDBD/i9rAtEW/lWNWJw",
                type = "EcdsaSecp256k1Signature2019"
            ),
            rawVCData = "{}"
        ),

        Credential(
            vcType = "Proof of GitHub",
            vcSchema = "http//exampleschemas.com/githubl.json",
            credentialStatus = CredentialStatus.Active,
            credentialSubjectData = "{\"username\":\"@user123\", \"stars\":\"200\", \"forks\":\"10\"}",
            expirationDate = 1735689600000L,
            issuanceDate = 1672531200000L,
            issuerDid = "did:mock:issuer:1234567",
            id = "claimid:456",
            holderDid = MOCK_DID,
            credentialProof = CredentialProof(
                creationDate = 1672531200000L,
                proofPurpose = "assertionMethod",
                verificationMethod = "${MOCK_DID}#primary",
                jws = "JTrUe7GOobRsZf5w+djg4qaAWDdukc4dg+kog3CwoiA9vJqqgUAhrTkashDrvxaFksR6Ngx1Cib+RV9X1XC/HccfoBTMMdkSe9t/IVhDBD/i9rAtEW/lWNWJw",
                type = "EcdsaSecp256k1Signature2019"
            ),
            rawVCData = "{}"
        ),

        Credential(
            vcType = "Proof of E-mail",
            vcSchema = "http//exampleschemas.com/email.json",
            credentialStatus = CredentialStatus.Expired,
            credentialSubjectData = "{\"email\":\"${MOCK_LOGIN}\"}",
            expirationDate = 1685635468000L,
            issuanceDate = 1672531200000L,
            issuerDid = "did:mock:issuer:1234567",
            id = "claimid:789",
            holderDid = MOCK_DID,
            credentialProof = CredentialProof(
                creationDate = 1672531200000L,
                proofPurpose = "assertionMethod",
                verificationMethod = "${MOCK_DID}#primary",
                jws = "JTrUe7GOobRsZf5w+djg4qaAWDdukc4dg+kog3CwoiA9vJqqgUAhrTkashDrvxaFksR6Ngx1Cib+RV9X1XC/HccfoBTMMdkSe9t/IVhDBD/i9rAtEW/lWNWJw",
                type = "EcdsaSecp256k1Signature2019"
            ),
            rawVCData = "{}"
        ),
    )
}