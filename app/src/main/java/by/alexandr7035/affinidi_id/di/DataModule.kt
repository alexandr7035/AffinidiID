package by.alexandr7035.affinidi_id.di

import android.app.Application
import android.content.Context
import by.alexandr7035.affinidi_id.core.network.AuthInterceptor
import by.alexandr7035.affinidi_id.core.network.ErrorInterceptor
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationHelper
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationHelperImpl
import by.alexandr7035.affinidi_id.domain.repository.*
import by.alexandr7035.data.api.ApiService
import by.alexandr7035.data.helpers.DicebearAvatarsHelper
import by.alexandr7035.data.helpers.DicebearAvatarsHelperImpl
import by.alexandr7035.data.repository.*
import by.alexandr7035.data.storage.ProfileStorage
import by.alexandr7035.data.storage.ProfileStorageImpl
import by.alexandr7035.data.storage.SecretsStorage
import by.alexandr7035.data.storage.SecretsStorageImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return  OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(ErrorInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .retryOnConnectionFailure(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://cloud-wallet-api.prod.affinity-project.org/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideProfileRepository(profileStorage: ProfileStorage, avatarsHelper: DicebearAvatarsHelper): ProfileRepository {
        return ProfileRepositoryImpl(profileStorage, avatarsHelper)
    }

    @Provides
    @Singleton
    fun provideAuthDataStorage(application: Application): ProfileStorage {
        return ProfileStorageImpl(application)
    }

    @Provides
    @Singleton
    fun provideAvatarsHelper(): DicebearAvatarsHelper {
        return DicebearAvatarsHelperImpl()
    }

    @Provides
    @Singleton
    fun provideSecretsStorage(@ApplicationContext context: Context): SecretsStorage {
        return SecretsStorageImpl(context)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(apiService: ApiService, secretsStorage: SecretsStorage): LoginRepository {
        return LoginRepositoryImpl(apiService, secretsStorage)
    }

    @Provides
    @Singleton
    fun provideRegistrationRepository(apiService: ApiService, secretsStorage: SecretsStorage): RegistrationRepository {
        return RegistrationRepositoryImpl(apiService, secretsStorage)
    }

    @Provides
    @Singleton
    fun provideResetPasswordRepository(apiService: ApiService): ResetPasswordRepository {
        return ResetPasswordRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideChangeProfileRepository(apiService: ApiService): ChangeProfileRepository {
        return ChangeProfileRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideInputValidationHelper(): InputValidationHelper {
        return InputValidationHelperImpl(minPasswordLength = 8)
    }
}