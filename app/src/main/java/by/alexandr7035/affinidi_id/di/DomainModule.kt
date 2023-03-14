package by.alexandr7035.affinidi_id.di

import by.alexandr7035.affinidi_id.domain.repository.*
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
    fun provideGetProfileUseCase(appSettings: AppSettings): GetProfileUseCase {
        return GetProfileUseCase(appSettings)
    }

    @Provides
    fun provideSignInWithEmailUseCase(loginRepository: LoginRepository): SignInWithEmailUseCase {
        return SignInWithEmailUseCase(loginRepository)
    }

    @Provides
    fun provideSignInWithRefreshTokenUseCase(loginRepository: LoginRepository): SignInWithRefreshTokenUseCase {
        return SignInWithRefreshTokenUseCase(loginRepository)
    }

    @Provides
    fun provideGetAuthStateUseCase(appSettings: AppSettings, signInWithRefreshTokenUseCase: SignInWithRefreshTokenUseCase): CheckIfAuthorizedUseCase {
        return CheckIfAuthorizedUseCase(appSettings, signInWithRefreshTokenUseCase)
    }

    @Provides
    fun provideLogOutUseCase(
        loginRepository: LoginRepository,
        setAppLockedWithBiometricsUseCase: by.alexandr7035.affinidi_id.domain.usecase.applock.SetAppLockedWithBiometricsUseCase
    ): LogOutUseCase {
        return LogOutUseCase(loginRepository, setAppLockedWithBiometricsUseCase)
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
    fun provideChangePasswordUseCase(changeProfileRepository: ChangeProfileRepository): ChangePasswordUseCase {
        return ChangePasswordUseCase(changeProfileRepository)
    }

    @Provides
    fun provideGetCredentialsListUseCase(storedCredentialsRepository: StoredCredentialsRepository): GetCredentialsListUseCase {
        return GetCredentialsListUseCase(storedCredentialsRepository)
    }

    @Provides
    fun provideIssueCredentialUseCase(
        issueCredentialsRepository: IssueCredentialsRepository,
        checkIfHaveCredentialUseCase: CheckIfHaveCredentialUseCase
    ): IssueCredentialUseCase {
        return IssueCredentialUseCase(issueCredentialsRepository, checkIfHaveCredentialUseCase)
    }

    @Provides
    fun provideGetAvailableVcTypesUseCase(): GetAvailableVcTypesUseCase {
        return GetAvailableVcTypesUseCase()
    }

    @Provides
    fun provideDeleteCredentialUseCase(
        storedCredentialsRepository: StoredCredentialsRepository
    ): DeleteCredentialUseCase {
        return DeleteCredentialUseCase(storedCredentialsRepository)
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
        return ShareCredentialUseCase(storedCredentialsRepository)
    }

    @Provides
    fun provideObtainCredentialWithQrCode(verificationRepository: VerificationRepository): ObtainCredentialWithQrCodeUseCase {
        return ObtainCredentialWithQrCodeUseCase(verificationRepository)
    }
}