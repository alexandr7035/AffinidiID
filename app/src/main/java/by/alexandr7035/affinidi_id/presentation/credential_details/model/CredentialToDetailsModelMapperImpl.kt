package by.alexandr7035.affinidi_id.presentation.credential_details.model

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card.CredentialCardMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_proof.CredentialProofToFieldsMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_status.CredentialStatusMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_subject.CredentialSubjectToFieldsMapper
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import javax.inject.Inject

class CredentialToDetailsModelMapperImpl @Inject constructor(
    private val credentialCardMapper: CredentialCardMapper,
    private val credentialStatusMapper: CredentialStatusMapper,
    private val credentialSubjectToFieldsMapper: CredentialSubjectToFieldsMapper,
    private val credentialProofToFieldsMapper: CredentialProofToFieldsMapper
): CredentialToDetailsModelMapper {
    override fun map(credential: Credential): CredentialDetailsUi.Success {

        // Credential card with primary fields
        val credentialCardData = credentialCardMapper.map(credential)
        val credentialStatusUi = credentialStatusMapper.map(credential.credentialStatus)

        // CredentialSubject (VC fields)
        // TODO handle errors inside
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonObject = gson.fromJson(credential.credentialSubjectData, JsonObject::class.java)
        val credentialSubjectItems = credentialSubjectToFieldsMapper.map(jsonObject)

        val json = gson.fromJson(credential.rawVCData, JsonObject::class.java)
        val prettyFormattedVC = gson.toJson(json, JsonObject::class.java)

        // Credential proof
        val credentialProofItems = credentialProofToFieldsMapper.map(credential.credentialProof)

        return CredentialDetailsUi.Success(
            credentialCardUi = credentialCardData,
            credentialSubjectItems = credentialSubjectItems,
            rawVcDataPrettyFormatted = prettyFormattedVC,
            proofItems = credentialProofItems,
            credentialStatus = credentialStatusUi
        )
    }
}