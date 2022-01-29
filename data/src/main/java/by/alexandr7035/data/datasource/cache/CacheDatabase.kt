package by.alexandr7035.data.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import by.alexandr7035.data.model.local.credentials.CredentialEntity
import by.alexandr7035.data.datasource.cache.credentials.CredentialsDAO

@Database(entities = [CredentialEntity::class], version = 1)
abstract class CacheDatabase: RoomDatabase() {
    abstract fun getCredentialsDao(): CredentialsDAO
}