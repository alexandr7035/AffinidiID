package by.alexandr7035.affinidi_id.di

import by.alexandr7035.affinidi_id.data.AuthRepository
import by.alexandr7035.affinidi_id.data.implementation.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provieAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }
}