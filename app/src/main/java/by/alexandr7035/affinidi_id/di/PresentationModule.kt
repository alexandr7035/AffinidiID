package by.alexandr7035.affinidi_id.di

import android.content.Context
import by.alexandr7035.affinidi_id.presentation.common.errors.ErrorTypeMapper
import by.alexandr7035.affinidi_id.presentation.common.errors.ErrorTypeMapperImpl
import by.alexandr7035.affinidi_id.presentation.credential_details.credential_ui.CredentialToDetailsModelMapper
import by.alexandr7035.affinidi_id.presentation.credential_details.credential_ui.CredentialToDetailsModelMapperImpl
import by.alexandr7035.affinidi_id.presentation.credential_details.credential_ui.credential_metadata.CredentialMetadataToFieldsMapper
import by.alexandr7035.affinidi_id.presentation.credential_details.credential_ui.credential_metadata.CredentialMetadataToFieldsMapperImpl
import by.alexandr7035.affinidi_id.presentation.credential_details.credential_ui.credential_proof.CredentialProofToFieldsMapper
import by.alexandr7035.affinidi_id.presentation.credential_details.credential_ui.credential_proof.CredentialProofToFieldsMapperImpl
import by.alexandr7035.affinidi_id.presentation.credential_details.credential_ui.credential_subject.CredentialSubjectToFieldsMapper
import by.alexandr7035.affinidi_id.presentation.credential_details.credential_ui.credential_subject.CredentialSubjectToFieldsMapperImpl
import by.alexandr7035.affinidi_id.presentation.credentials_list.CredentialsListMapper
import by.alexandr7035.affinidi_id.presentation.credentials_list.CredentialsListMapperImpl
import by.alexandr7035.affinidi_id.presentation.helpers.mappers.CredentialStatusMapper
import by.alexandr7035.affinidi_id.presentation.helpers.mappers.CredentialStatusMapperImpl
import by.alexandr7035.affinidi_id.presentation.helpers.resources.ResourceProvider
import by.alexandr7035.affinidi_id.presentation.helpers.resources.ResourceProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object PresentationModule {

    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProviderImpl(context)
    }

    @Provides
    fun provideCredentialsListMapper(
        resourceProvider: ResourceProvider,
        credentialStatusMapper: CredentialStatusMapper,
        errorTypeMapper: ErrorTypeMapper
    ): CredentialsListMapper {
        return CredentialsListMapperImpl(resourceProvider, credentialStatusMapper, errorTypeMapper)
    }

    @Provides
    fun provideCredentialToDetailsModelMapper(
        statusMapper: CredentialStatusMapper,
        credentialSubjectMapper: CredentialSubjectToFieldsMapper,
        metadataMapper: CredentialMetadataToFieldsMapper,
        proofMapper: CredentialProofToFieldsMapper
    ): CredentialToDetailsModelMapper {
        return CredentialToDetailsModelMapperImpl(statusMapper, credentialSubjectMapper, metadataMapper, proofMapper)
    }


    @Provides
    fun provideCredentialStatusMapper(resourceProvider: ResourceProvider): CredentialStatusMapper {
        return CredentialStatusMapperImpl(resourceProvider)
    }

    @Provides
    fun provideCredentialSubjectToFieldsMapper(): CredentialSubjectToFieldsMapper {
        return CredentialSubjectToFieldsMapperImpl()
    }

    @Provides
    fun provideCredentialMetadataToFieldsMapper(resourceProvider: ResourceProvider): CredentialMetadataToFieldsMapper {
        return CredentialMetadataToFieldsMapperImpl(resourceProvider)
    }

    @Provides
    fun provideCredentialProofToFieldsMapper(resourceProvider: ResourceProvider): CredentialProofToFieldsMapper {
        return CredentialProofToFieldsMapperImpl(resourceProvider)
    }

    @Provides
    fun provideErrorTypeMapper(resourceProvider: ResourceProvider): ErrorTypeMapper {
        return ErrorTypeMapperImpl(resourceProvider)
    }
}