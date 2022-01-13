package by.alexandr7035.affinidi_id.di

import android.app.Application
import by.alexandr7035.data.repository.ProfileRepositoryImpl
import by.alexandr7035.affinidi_id.domain.repository.ProfileRepository
import by.alexandr7035.data.helpers.DicebearAvatarsHelper
import by.alexandr7035.data.helpers.DicebearAvatarsHelperImpl
import by.alexandr7035.data.storage.AuthDataStorage
import by.alexandr7035.data.storage.AuthDataStorageImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideProfileRepository(authDataStorage: AuthDataStorage, avatarsHelper: DicebearAvatarsHelper): ProfileRepository {
        return ProfileRepositoryImpl(authDataStorage, avatarsHelper)
    }

    @Provides
    @Singleton
    fun provideAuthDataStorage(application: Application): AuthDataStorage {
        return AuthDataStorageImpl(application)
    }

    @Provides
    @Singleton
    fun provideAvatarsHelper(): DicebearAvatarsHelper {
        return DicebearAvatarsHelperImpl()
    }

}