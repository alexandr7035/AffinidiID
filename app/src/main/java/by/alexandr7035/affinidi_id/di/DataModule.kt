package by.alexandr7035.affinidi_id.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import by.alexandr7035.data.network.interceptors.AuthInterceptor
import by.alexandr7035.data.network.interceptors.ErrorInterceptor
import by.alexandr7035.affinidi_id.presentation.helpers.validation.InputValidationHelper
import by.alexandr7035.affinidi_id.presentation.helpers.validation.InputValidationHelperImpl
import by.alexandr7035.affinidi_id.domain.repository.*
import by.alexandr7035.data.network.api.UserApiService
import by.alexandr7035.data.helpers.profile_avatars.DicebearAvatarsHelper
import by.alexandr7035.data.helpers.profile_avatars.DicebearAvatarsHelperImpl
import by.alexandr7035.data.helpers.vc_issuance.VCIssuanceHelper
import by.alexandr7035.data.helpers.vc_issuance.VCIssuanceHelperImpl
import by.alexandr7035.data.helpers.vc_mapping.CredentialSubjectCaster
import by.alexandr7035.data.helpers.vc_mapping.CredentialSubjectCasterImpl
import by.alexandr7035.data.helpers.vc_mapping.SignedCredentialToDomainMapper
import by.alexandr7035.data.helpers.vc_mapping.SignedCredentialToDomainMapperImpl
import by.alexandr7035.data.local_storage.CacheDatabase
import by.alexandr7035.data.local_storage.credentials.CredentialsCacheDataSource
import by.alexandr7035.data.local_storage.credentials.CredentialsCacheDataSourceImpl
import by.alexandr7035.data.local_storage.credentials.CredentialsDAO
import by.alexandr7035.data.network.api.CredentialsApiService
import by.alexandr7035.data.repository.*
import by.alexandr7035.data.local_storage.profile.ProfileStorage
import by.alexandr7035.data.local_storage.profile.ProfileStorageImpl
import by.alexandr7035.data.local_storage.secrets.SecretsStorage
import by.alexandr7035.data.local_storage.secrets.SecretsStorageImpl
import by.alexandr7035.data.network.CredentialsCloudDataSource
import by.alexandr7035.data.network.CredentialsCloudDataSourceImpl
import com.google.gson.Gson
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
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCredentialsApiService(retrofit: Retrofit): CredentialsApiService {
        return retrofit.create(CredentialsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(userApiService: UserApiService, secretsStorage: SecretsStorage): LoginRepository {
        return LoginRepositoryImpl(userApiService, secretsStorage)
    }

    @Provides
    @Singleton
    fun provideRegistrationRepository(userApiService: UserApiService, secretsStorage: SecretsStorage): RegistrationRepository {
        return RegistrationRepositoryImpl(userApiService, secretsStorage)
    }

    @Provides
    @Singleton
    fun provideResetPasswordRepository(userApiService: UserApiService): ResetPasswordRepository {
        return ResetPasswordRepositoryImpl(userApiService)
    }

    @Provides
    @Singleton
    fun provideChangeProfileRepository(userApiService: UserApiService): ChangeProfileRepository {
        return ChangeProfileRepositoryImpl(userApiService)
    }

    @Provides
    @Singleton
    fun provideInputValidationHelper(): InputValidationHelper {
        return InputValidationHelperImpl(minPasswordLength = 8)
    }

    @Provides
    @Singleton
    fun provideVCIssuanceHelper(credentialsApiService: CredentialsApiService): VCIssuanceHelper {
        return VCIssuanceHelperImpl(credentialsApiService)
    }

    @Provides
    @Singleton
    fun provideCredentialsCloudDataSource(credentialsApiService: CredentialsApiService, credentialToDomainMapper: SignedCredentialToDomainMapper): CredentialsCloudDataSource {
        return CredentialsCloudDataSourceImpl(credentialsApiService, credentialToDomainMapper)
    }

    @Provides
    @Singleton
    fun provideCredentialsCacheDataSource(credentialsDAO: CredentialsDAO): CredentialsCacheDataSource {
        return CredentialsCacheDataSourceImpl(credentialsDAO)
    }

    @Provides
    @Singleton
    fun provideCredentialsRepository(
        credentialsApiService: CredentialsApiService,
        vcIssuanceHelper: VCIssuanceHelper,
        credentialsCloudDataSource: CredentialsCloudDataSource,
        credentialsCacheDataSource: CredentialsCacheDataSource
    ): CredentialsRepository {
        return CredentialsRepositoryImpl(credentialsApiService, vcIssuanceHelper, credentialsCloudDataSource, credentialsCacheDataSource)
    }


    @Provides
    @Singleton
    fun provideCredentialSubjectCaster(gson: Gson): CredentialSubjectCaster {
        return CredentialSubjectCasterImpl(gson)
    }

    @Provides
    @Singleton
    fun provideSignedCredentialToDomainMapper(credentialSubjectCaster: CredentialSubjectCaster): SignedCredentialToDomainMapper {
        return SignedCredentialToDomainMapperImpl(credentialSubjectCaster)
    }

    @Provides
    @Singleton
    fun provideAuthCheckRepository(userApiService: UserApiService): AuthCheckRepository {
        return AuthCheckRepositoryImpl(userApiService)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideCredentialsDAO(database: CacheDatabase): CredentialsDAO {
        return database.getCredentialsDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CacheDatabase {
        return Room
            .databaseBuilder(context, CacheDatabase::class.java, "cache.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}