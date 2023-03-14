package by.alexandr7035.affinidi_id.di

import by.alexandr7035.affinidi_id.domain.repository.*
import by.alexandr7035.affinidi_id.domain.usecase.applock.SetAppLockedWithBiometricsUseCase
import by.alexandr7035.affinidi_id.domain.usecase.credentials.*
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
    fun provideGetAuthStateUseCase(loginRepository: LoginRepository): CheckIfAuthorizedUseCase {
        return CheckIfAuthorizedUseCase(loginRepository)
    }

    @Provides
    fun provideLogOutUseCase(
        loginRepository: LoginRepository,
        checkIfAuthorizedUseCase: CheckIfAuthorizedUseCase,
        clearProfileUseCase: ClearProfileUseCase,
        setAppLockedWithBiometricsUseCase: SetAppLockedWithBiometricsUseCase
    ): LogOutUseCase {
        return LogOutUseCase(loginRepository, checkIfAuthorizedUseCase, clearProfileUseCase, setAppLockedWithBiometricsUseCase)
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
    fun provideChangePasswordUseCase(changeProfileRepository: ChangeProfileRepository, checkIfAuthorizedUseCase: CheckIfAuthorizedUseCase): ChangePasswordUseCase {
        return ChangePasswordUseCase(changeProfileRepository, checkIfAuthorizedUseCase)
    }

    @Provides
    fun provideGetCredentialsListUseCase(storedCredentialsRepository: StoredCredentialsRepository, authStateUseCase: CheckIfAuthorizedUseCase): GetCredentialsListUseCase {
        return GetCredentialsListUseCase(storedCredentialsRepository, authStateUseCase)
    }

    @Provides
    fun provideIssueCredentialUseCase(issueCredentialsRepository: IssueCredentialsRepository, authStateUseCase: CheckIfAuthorizedUseCase, checkIfHaveCredentialUseCase: CheckIfHaveCredentialUseCase): IssueCredentialUseCase {
        return IssueCredentialUseCase(issueCredentialsRepository, authStateUseCase, checkIfHaveCredentialUseCase)
    }

    @Provides
    fun provideGetAvailableVcTypesUseCase(): GetAvailableVcTypesUseCase {
        return GetAvailableVcTypesUseCase()
    }

    @Provides
    fun provideDeleteCredentialUseCase(storedCredentialsRepository: StoredCredentialsRepository, checkIfAuthorizedUseCase: CheckIfAuthorizedUseCase): DeleteCredentialUseCase {
        return DeleteCredentialUseCase(storedCredentialsRepository, checkIfAuthorizedUseCase)
    }

    @Provides
    fun provideGetCredentialByIdUseCase(storedCredentialsRepository: StoredCredentialsRepository, authStateUseCase: CheckIfAuthorizedUseCase): GetCredentialByIdUseCase {
        return GetCredentialByIdUseCase(storedCredentialsRepository, authStateUseCase)
    }

    @Provides
    fun provideVerifyCredentialUseCase(verificationRepository: VerificationRepository): VerifyCredentialUseCase {
        return VerifyCredentialUseCase(verificationRepository)
    }

    @Provides
    fun provideCheckIfHaveCredentialUseCase(storedCredentialsRepository: StoredCredentialsRepository): CheckIfHaveCredentialUseCase {
        return CheckIfHaveCredentialUseCase(storedCredentialsRepository)
    }

    @Provides
    fun provideShareCredentialUseCase(storedCredentialsRepository: StoredCredentialsRepository, checkIfAuthorizedUseCase: CheckIfAuthorizedUseCase): ShareCredentialUseCase {
        return ShareCredentialUseCase(storedCredentialsRepository, checkIfAuthorizedUseCase)
    }

    @Provides
    fun provideObtainCredentialWithQrCode(verificationRepository: VerificationRepository): ObtainCredentialWithQrCodeUseCase {
        return ObtainCredentialWithQrCodeUseCase(verificationRepository)
    }
}