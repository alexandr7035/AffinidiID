package by.alexandr7035.affinidi_id.di

import android.app.Application
import by.alexandr7035.affinidi_id.core.AuthInterceptor
import by.alexandr7035.affinidi_id.core.network.ErrorInterceptor
import by.alexandr7035.affinidi_id.data.ApiService
import by.alexandr7035.affinidi_id.data.AuthDataStorage
import by.alexandr7035.affinidi_id.data.AuthRepository
import by.alexandr7035.affinidi_id.data.implementation.AuthDataStorageImpl
import by.alexandr7035.affinidi_id.data.implementation.AuthRepositoryImpl
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
    fun provideAuthDataStorage(application: Application): AuthDataStorage {
        return AuthDataStorageImpl(application)
    }
}