package by.alexandr7035.affinidi_id.di

import by.alexandr7035.affinidi_id.domain.repository.*
import by.alexandr7035.affinidi_id.domain.usecase.user.*
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
    fun provideClearProfileUseCase(profileRepository: ProfileRepository): ClearProfileUseCase {
        return ClearProfileUseCase(profileRepository)
    }

    @Provides
    fun provideSaveProfileUseCase(profileRepository: ProfileRepository): SaveProfileUseCase {
        return SaveProfileUseCase(profileRepository)
    }

    @Provides
    fun provideSignInWithEmailUseCase(loginRepository: LoginRepository): SignInWithEmailUseCase {
        return SignInWithEmailUseCase(loginRepository)
    }

    @Provides
    fun provideGetAuthStateUseCase(loginRepository: LoginRepository): GetAuthStateUseCase {
        return GetAuthStateUseCase(loginRepository)
    }

    @Provides
    fun provideLogOutUseCase(loginRepository: LoginRepository, getAuthStateUseCase: GetAuthStateUseCase, clearProfileUseCase: ClearProfileUseCase): LogOutUseCase {
        return LogOutUseCase(loginRepository, getAuthStateUseCase, clearProfileUseCase)
    }

    @Provides
    fun provideRegisterWithEmailUseCase(registrationRepository: RegistrationRepository): RegisterWithEmailUseCase {
        return RegisterWithEmailUseCase(registrationRepository)
    }

    @Provides
    fun provideConfirmRegisterWithEmailUseCase(registrationRepository: RegistrationRepository): ConfirmRegisterWithEmailUseCase {
        return ConfirmRegisterWithEmailUseCase(registrationRepository)
    }

    @Provides
    fun provideInitializePasswordResetUseCase(resetPasswordRepository: ResetPasswordRepository): InitializePasswordResetUseCase {
        return InitializePasswordResetUseCase(resetPasswordRepository)
    }

    @Provides
    fun provideConfirmPasswordResetUseCase(resetPasswordRepository: ResetPasswordRepository): ConfirmPasswordResetUseCase {
        return ConfirmPasswordResetUseCase(resetPasswordRepository)
    }

    @Provides
    fun provideChangePasswordUseCase(changeProfileRepository: ChangeProfileRepository, getAuthStateUseCase: GetAuthStateUseCase): ChangePasswordUseCase {
        return ChangePasswordUseCase(changeProfileRepository, getAuthStateUseCase)
    }
}