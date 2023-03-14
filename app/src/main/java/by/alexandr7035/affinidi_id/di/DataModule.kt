package by.alexandr7035.affinidi_id.di

import android.content.Context
import androidx.room.Room
import by.alexandr7035.affinidi_id.domain.repository.*
import by.alexandr7035.affinidi_id.presentation.common.validation.InputValidationHelper
import by.alexandr7035.affinidi_id.presentation.common.validation.InputValidationHelperImpl
import by.alexandr7035.data.datasource.cache.CacheDatabase
import by.alexandr7035.affinidi_id.domain.repository.AppSettings
import by.alexandr7035.data.datasource.cache.app.AppSettingsImpl
import by.alexandr7035.data.datasource.cache.credentials.CredentialsCacheDataSource
import by.alexandr7035.data.datasource.cache.credentials.CredentialsCacheDataSourceImpl
import by.alexandr7035.data.datasource.cache.credentials.CredentialsDAO
import by.alexandr7035.data.datasource.cache.profile.ProfileStorage
import by.alexandr7035.data.datasource.cache.profile.ProfileStorageImpl
import by.alexandr7035.data.datasource.cache.secrets.SecretsStorage
import by.alexandr7035.data.datasource.cache.secrets.SecretsStorageImpl
import by.alexandr7035.data.datasource.cloud.ApiCallHelper
import by.alexandr7035.data.datasource.cloud.ApiCallHelperImpl
import by.alexandr7035.data.datasource.cloud.CredentialsCloudDataSource
import by.alexandr7035.data.datasource.cloud.CredentialsCloudDataSourceImpl
import by.alexandr7035.data.datasource.cloud.api.CredentialsApiService
import by.alexandr7035.data.datasource.cloud.api.UserApiService
import by.alexandr7035.data.datasource.cloud.interceptors.AuthInterceptor
import by.alexandr7035.data.datasource.cloud.interceptors.ErrorInterceptor
import by.alexandr7035.data.datasource.cloud.interceptors.NullBodyHandlerInterceptor
import by.alexandr7035.data.helpers.profile_avatars.DicebearAvatarsHelper
import by.alexandr7035.data.helpers.profile_avatars.DicebearAvatarsHelperImpl
import by.alexandr7035.data.helpers.vc_issuance.VCIssuanceHelper
import by.alexandr7035.data.helpers.vc_issuance.VCIssuanceHelperImpl
import by.alexandr7035.data.helpers.vc_mapping.CredentialSubjectCaster
import by.alexandr7035.data.helpers.vc_mapping.CredentialSubjectCasterImpl
import by.alexandr7035.data.helpers.vc_mapping.SignedCredentialToDomainMapper
import by.alexandr7035.data.helpers.vc_mapping.SignedCredentialToDomainMapperImpl
import by.alexandr7035.data.repository.*
import com.cioccarellia.ksprefs.KsPrefs
import com.cioccarellia.ksprefs.config.EncryptionType
import com.cioccarellia.ksprefs.config.model.AutoSavePolicy
import com.cioccarellia.ksprefs.config.model.CommitStrategy
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
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(ErrorInterceptor())
            .addInterceptor(NullBodyHandlerInterceptor())
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
    fun provideApiCallHelper(): ApiCallHelper {
        return ApiCallHelperImpl()
    }

    @Provides
    @Singleton
    fun provideProfileRepository(profileStorage: ProfileStorage, avatarsHelper: DicebearAvatarsHelper): ProfileRepository {
        return ProfileRepositoryImpl(profileStorage, avatarsHelper)
    }

    @Provides
    @Singleton
    fun provideAuthDataStorage(@ApplicationContext context: Context): ProfileStorage {
        return ProfileStorageImpl(context)
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
    fun provideLoginRepository(
        userApiService: UserApiService,
        apiCallHelper: ApiCallHelper,
        secretsStorage: SecretsStorage,
        credentialsCacheDataSource: CredentialsCacheDataSource
    ): LoginRepository {
        return LoginRepositoryImpl(userApiService, apiCallHelper, secretsStorage, credentialsCacheDataSource)
    }

    @Provides
    @Singleton
    fun provideRegistrationRepository(
        userApiService: UserApiService,
        apiCallHelper: ApiCallHelper,
        secretsStorage: SecretsStorage
    ): RegistrationRepository {
        return RegistrationRepositoryImpl(userApiService, apiCallHelper, secretsStorage)
    }

    @Provides
    @Singleton
    fun provideResetPasswordRepository(userApiService: UserApiService, apiCallHelper: ApiCallHelper): ResetPasswordRepository {
        return ResetPasswordRepositoryImpl(userApiService, apiCallHelper)
    }

    @Provides
    @Singleton
    fun provideChangeProfileRepository(userApiService: UserApiService, apiCallHelper: ApiCallHelper): ChangeProfileRepository {
        return ChangeProfileRepositoryImpl(userApiService, apiCallHelper)
    }

    @Provides
    @Singleton
    fun provideInputValidationHelper(): InputValidationHelper {
        return InputValidationHelperImpl(minPasswordLength = 8)
    }

    @Provides
    @Singleton
    fun provideVCIssuanceHelper(
        credentialsApiService: CredentialsApiService,
        credentialSubjectCaster: CredentialSubjectCaster
    ): VCIssuanceHelper {
        return VCIssuanceHelperImpl(credentialsApiService, credentialSubjectCaster)
    }

    @Provides
    @Singleton
    fun provideCredentialsCloudDataSource(
        credentialsApiService: CredentialsApiService,
        apiCallHelper: ApiCallHelper
    ): CredentialsCloudDataSource {
        return CredentialsCloudDataSourceImpl(credentialsApiService, apiCallHelper)
    }

    @Provides
    @Singleton
    fun provideCredentialsCacheDataSource(credentialsDAO: CredentialsDAO, gson: Gson): CredentialsCacheDataSource {
        return CredentialsCacheDataSourceImpl(credentialsDAO, gson)
    }

    @Provides
    @Singleton
    fun provideCredentialsRepository(
        credentialsApiService: CredentialsApiService,
        apiCallHelper: ApiCallHelper,
        credentialsCloudDataSource: CredentialsCloudDataSource,
        credentialsCacheDataSource: CredentialsCacheDataSource,
        credentialToDomainMapper: SignedCredentialToDomainMapper,
    ): StoredCredentialsRepository {
        return StoredCredentialsRepositoryImpl(
            credentialsApiService,
            apiCallHelper,
            credentialsCloudDataSource,
            credentialsCacheDataSource,
            credentialToDomainMapper,
        )
    }


    @Provides
    @Singleton
    fun provideCredentialSubjectCaster(gson: Gson): CredentialSubjectCaster {
        return CredentialSubjectCasterImpl(gson)
    }

    @Provides
    @Singleton
    fun provideSignedCredentialToDomainMapper(gson: Gson): SignedCredentialToDomainMapper {
        return SignedCredentialToDomainMapperImpl(gson)
    }

    @Provides
    @Singleton
    fun provideIssueCredentialsRepository(vcIssuanceHelper: VCIssuanceHelper): IssueCredentialsRepository {
        return IssueCredentialsRepositoryImpl(vcIssuanceHelper)
    }

    @Provides
    @Singleton
    fun provideVerificationRepository(
        credentialsApiService: CredentialsApiService,
        apiCallHelper: ApiCallHelper,
        signedCredentialToDomainMapper: SignedCredentialToDomainMapper,
        gson: Gson
    ): VerificationRepository {
        return VerificationRepositoryImpl(credentialsApiService, apiCallHelper, signedCredentialToDomainMapper, gson)
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

    @Provides
    @Singleton
    fun provideKeyValueStorage(@ApplicationContext context: Context): KsPrefs {
        return KsPrefs(context) {
            // Configuration Parameters Lambda
            encryptionType = EncryptionType.PlainText()
            autoSave = AutoSavePolicy.AUTOMATIC
            commitStrategy = CommitStrategy.COMMIT
        }
    }

    @Provides
    @Singleton
    fun provideAppSettings(prefs: KsPrefs): AppSettings {
        return AppSettingsImpl(prefs)
    }
}