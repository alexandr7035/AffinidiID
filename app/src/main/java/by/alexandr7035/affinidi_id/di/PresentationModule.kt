package by.alexandr7035.affinidi_id.di

import android.content.Context
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card.CredentialCardMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card.CredentialCardMapperImpl
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_proof.CredentialProofToFieldsMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_proof.CredentialProofToFieldsMapperImpl
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_status.CredentialStatusMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_status.CredentialStatusMapperImpl
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_subject.CredentialSubjectToFieldsMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_subject.CredentialSubjectToFieldsMapperImpl
import by.alexandr7035.affinidi_id.presentation.common.credentials.verification.VerificationResultToUiMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.verification.VerificationResultToUiMapperImpl
import by.alexandr7035.affinidi_id.presentation.common.errors.ErrorTypeMapper
import by.alexandr7035.affinidi_id.presentation.common.errors.ErrorTypeMapperImpl
import by.alexandr7035.affinidi_id.presentation.common.permissions.PermissionsHelper
import by.alexandr7035.affinidi_id.presentation.common.permissions.PermissionsHelperImpl
import by.alexandr7035.affinidi_id.presentation.common.permissions.PermissionsPreferences
import by.alexandr7035.affinidi_id.presentation.common.permissions.PermissionsPreferencesImpl
import by.alexandr7035.affinidi_id.presentation.common.resources.ResourceProvider
import by.alexandr7035.affinidi_id.presentation.common.resources.ResourceProviderImpl
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialToDetailsModelMapper
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialToDetailsModelMapperImpl
import by.alexandr7035.affinidi_id.presentation.credentials_list.CredentialsListMapper
import by.alexandr7035.affinidi_id.presentation.credentials_list.CredentialsListMapperImpl
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
    fun providePermissionsPreferences(@ApplicationContext context: Context): PermissionsPreferences {
        return PermissionsPreferencesImpl(context)
    }

    @Provides
    fun providePermissionsHelper(permissionsPreferences: PermissionsPreferences): PermissionsHelper {
        return PermissionsHelperImpl(permissionsPreferences)
    }

    @Provides
    fun provideCredentialsListMapper(
        errorTypeMapper: ErrorTypeMapper,
        credentialCardMapper: CredentialCardMapper
    ): CredentialsListMapper {
        return CredentialsListMapperImpl(credentialCardMapper, errorTypeMapper)
    }

    @Provides
    fun provideCredentialCardMapper(resourceProvider: ResourceProvider, credentialStatusMapper: CredentialStatusMapper): CredentialCardMapper {
        return CredentialCardMapperImpl(resourceProvider, credentialStatusMapper)
    }

    @Provides
    fun provideCredentialToDetailsModelMapper(
        credentialCardMapper: CredentialCardMapper,
        credentialSubjectMapper: CredentialSubjectToFieldsMapper,
        proofMapper: CredentialProofToFieldsMapper,
        statusMapper: CredentialStatusMapper,
        ): CredentialToDetailsModelMapper {
        return CredentialToDetailsModelMapperImpl(credentialCardMapper, statusMapper, credentialSubjectMapper, proofMapper)
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
    fun provideCredentialProofToFieldsMapper(resourceProvider: ResourceProvider): CredentialProofToFieldsMapper {
        return CredentialProofToFieldsMapperImpl(resourceProvider)
    }

    @Provides
    fun provideErrorTypeMapper(resourceProvider: ResourceProvider): ErrorTypeMapper {
        return ErrorTypeMapperImpl(resourceProvider)
    }

    @Provides
    fun provideVerificationResultToUiMapper(resourceProvider: ResourceProvider): VerificationResultToUiMapper {
        return VerificationResultToUiMapperImpl(resourceProvider)
    }
}