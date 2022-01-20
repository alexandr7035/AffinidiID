package by.alexandr7035.affinidi_id.di

import android.content.Context
import by.alexandr7035.affinidi_id.presentation.credentials_list.CredentialsListMapper
import by.alexandr7035.affinidi_id.presentation.credentials_list.CredentialsListMapperImpl
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
    fun provideCredentialsListMapper(resourceProvider: ResourceProvider): CredentialsListMapper {
        return CredentialsListMapperImpl(resourceProvider)
    }
}