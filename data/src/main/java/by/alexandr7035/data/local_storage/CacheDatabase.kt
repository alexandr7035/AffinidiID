package by.alexandr7035.data.local_storage

import androidx.room.Database
import androidx.room.RoomDatabase
import by.alexandr7035.data.local_storage.credentials.CredentialEntity
import by.alexandr7035.data.local_storage.credentials.CredentialsDAO

@Database(entities = [CredentialEntity::class], version = 1)
abstract class CacheDatabase: RoomDatabase() {
    abstract fun getCredentialsDao(): CredentialsDAO
}