package by.alexandr7035.data.repository_mock

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialProof
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus

object MockConstants {
    const val MOCK_LOGIN = "test@mail.com"
    const val MOCK_PASSWORD = "1234567Ab"
    const val MOCK_DID = "did:mock:1234567abcd"
    const val MOCK_REQ_DELAY_MILLS = 1000L

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