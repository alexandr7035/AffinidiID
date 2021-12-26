package by.alexandr7035.affinidi_id.di

import android.app.Application
import by.alexandr7035.affinidi_id.core.network.AuthInterceptor
import by.alexandr7035.affinidi_id.core.network.ErrorInterceptor
import by.alexandr7035.affinidi_id.data.*
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationHelper
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationHelperImpl
import by.alexandr7035.affinidi_id.data.implementation.AuthDataStorageImpl
import by.alexandr7035.affinidi_id.data.implementation.AuthRepositoryImpl
import by.alexandr7035.affinidi_id.data.implementation.DicebearAvatarsHelperImpl
import by.alexandr7035.affinidi_id.data.implementation.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // FIXME move to network module
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

    // FIXME move to network module
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
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provieAuthRepository(apiService: ApiService, authDataStorage: AuthDataStorage): AuthRepository {
        return AuthRepositoryImpl(apiService, authDataStorage)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(authDataStorage: AuthDataStorage, avatarsHelper: DicebearAvatarsHelper, apiService: ApiService): ProfileRepository {
        return ProfileRepositoryImpl(authDataStorage, avatarsHelper, apiService)
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