package by.alexandr7035.affinidi_id.di

import by.alexandr7035.affinidi_id.domain.repository.LoginRepository
import by.alexandr7035.affinidi_id.domain.repository.ProfileRepository
import by.alexandr7035.affinidi_id.domain.usecase.GetAuthStateUseCase
import by.alexandr7035.affinidi_id.domain.usecase.GetProfileUseCase
import by.alexandr7035.affinidi_id.domain.usecase.SignInWithEmailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideGetProfileUseCase(profileRepository: ProfileRepository): GetProfileUseCase {
        return GetProfileUseCase(profileRepository)
    }

    @Provides
    fun provideSignInWithEmailUseCase(loginRepository: LoginRepository): SignInWithEmailUseCase {
        return SignInWithEmailUseCase(loginRepository)
    }

    @Provides
    fun provideGetAuthStateUseCase(loginRepository: LoginRepository): GetAuthStateUseCase {
        return GetAuthStateUseCase(loginRepository)
    }
}