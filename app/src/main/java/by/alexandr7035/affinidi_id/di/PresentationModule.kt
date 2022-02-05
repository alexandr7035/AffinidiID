package by.alexandr7035.affinidi_id.di

import android.content.Context
import by.alexandr7035.affinidi_id.presentation.common.errors.ErrorTypeMapper
import by.alexandr7035.affinidi_id.presentation.common.errors.ErrorTypeMapperImpl
import by.alexandr7035.affinidi_id.presentation.credential_details.credential_subject.CredentialSubjectToFieldsMapper
import by.alexandr7035.affinidi_id.presentation.credential_details.credential_subject.CredentialSubjectToFieldsMapperImpl
import by.alexandr7035.affinidi_id.presentation.credentials_list.CredentialsListMapper
import by.alexandr7035.affinidi_id.presentation.credentials_list.CredentialsListMapperImpl
import by.alexandr7035.affinidi_id.presentation.helpers.mappers.CredentialStatusMapper
import by.alexandr7035.affinidi_id.presentation.helpers.mappers.CredentialStatusMapperImpl
import by.alexandr7035.affinidi_id.presentation.helpers.mappers.CredentialTypeMapper
import by.alexandr7035.affinidi_id.presentation.helpers.mappers.CredentialTypeMapperImpl
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
        credentialTypeMapper: CredentialTypeMapper,
        errorTypeMapper: ErrorTypeMapper
    ): CredentialsListMapper {
        return CredentialsListMapperImpl(resourceProvider, credentialStatusMapper, credentialTypeMapper, errorTypeMapper)
    }

    @Provides
    fun provideCredentialTypeMapper(resourceProvider: ResourceProvider): CredentialTypeMapper {
        return CredentialTypeMapperImpl(resourceProvider)
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
    fun provideErrorTypeMapper(resourceProvider: ResourceProvider): ErrorTypeMapper {
        return ErrorTypeMapperImpl(resourceProvider)
    }
}