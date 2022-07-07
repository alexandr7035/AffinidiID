package by.alexandr7035.affinidi_id.presentation.credential_details.model

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card.CredentialCardMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_metadata.CredentialMetadataToFieldsMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_proof.CredentialProofToFieldsMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_subject.CredentialSubjectToFieldsMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_status.CredentialStatusMapper
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import javax.inject.Inject

class CredentialToDetailsModelMapperImpl @Inject constructor(
    private val credentialCardMapper: CredentialCardMapper,
    private val credentialStatusMapper: CredentialStatusMapper,
    private val credentialSubjectToFieldsMapper: CredentialSubjectToFieldsMapper,
    private val credentialMetadataToFieldsMapper: CredentialMetadataToFieldsMapper,
    private val credentialProofToFieldsMapper: CredentialProofToFieldsMapper
): CredentialToDetailsModelMapper {
    override fun map(credential: Credential): CredentialDetailsUi.Success {

        val credentialCardData = credentialCardMapper.map(credential)

        val credentialStatusUi = credentialStatusMapper.map(credential.credentialStatus)

        val metadataItems = credentialMetadataToFieldsMapper.map(credential)
        val credentialProofItems = credentialProofToFieldsMapper.map(credential.credentialProof)

        // FIXME domain abstraction
        val gson = GsonBuilder().setPrettyPrinting().create()

        val jsonObject = gson.fromJson(credential.credentialSubjectData, JsonObject::class.java)
        // TODO handle errors inside
        val credentialSubjectItems = credentialSubjectToFieldsMapper.map(jsonObject)

        val json = gson.fromJson(credential.rawVCData, JsonObject::class.java)
        val prettyFormattedVC = gson.toJson(json, JsonObject::class.java)

        return CredentialDetailsUi.Success(
            credentialCardUi = credentialCardData,
            metadataItems = metadataItems,
            credentialSubjectItems = credentialSubjectItems,
            rawVcDataPrettyFormatted = prettyFormattedVC,
            proofItems = credentialProofItems,
            credentialStatus = credentialStatusUi
        )
    }
}