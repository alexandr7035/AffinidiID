package by.alexandr7035.affinidi_id.di

import android.app.Application
import by.alexandr7035.affinidi_id.data.*
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationHelper
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationHelperImpl
import by.alexandr7035.affinidi_id.data.implementation.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRegistrationRepository(apiService: ApiService, authDataStorage: AuthDataStorage): RegistrationRepository {
        return RegistrationRepositoryImpl(apiService, authDataStorage)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(apiService: ApiService, authDataStorage: AuthDataStorage): LoginRepository {
        return LoginRepositoryImpl(apiService, authDataStorage)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(authDataStorage: AuthDataStorage, avatarsHelper: DicebearAvatarsHelper, apiService: ApiService): ProfileRepository {
        return ProfileRepositoryImpl(authDataStorage, avatarsHelper, apiService)
    }

    @Provides
    @Singleton
    fun provideResetPasswordRepository(apiService: ApiService): ResetPasswordRepository {
        return ResetPasswordRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideChangeProfileRepository(apiService: ApiService, authDataStorage: AuthDataStorage): ChangeProfileRepository {
        return ChangeProfileRepositoryImpl(apiService, authDataStorage)
    }

    @Provides
    @Singleton
    fun provideAuthDataStorage(application: Application): AuthDataStorage {
        return AuthDataStorageImpl(application)
    }

    @Provides
    fun provideDicebearAvatarsHelper(): DicebearAvatarsHelper {
        return DicebearAvatarsHelperImpl()
    }

    @Provides
    fun provideInputValidationHelper(): InputValidationHelper {
        return InputValidationHelperImpl(minPasswordLength = 8)
    }
}