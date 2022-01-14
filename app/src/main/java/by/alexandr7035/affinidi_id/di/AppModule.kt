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
    fun provideInputValidationHelper(): InputValidationHelper {
        return InputValidationHelperImpl(minPasswordLength = 8)
    }
}