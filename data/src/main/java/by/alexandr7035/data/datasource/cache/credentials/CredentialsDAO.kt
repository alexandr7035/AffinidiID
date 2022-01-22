package by.alexandr7035.data.datasource.cache.credentials

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.alexandr7035.data.model.local.credentials.CredentialEntity

@Dao
interface CredentialsDAO {
    @Query("SELECT * FROM credentials")
    suspend fun getCredentials(): List<CredentialEntity>

    @Query("SELECT * FROM credentials WHERE credentialId = (:credentialId)")
    suspend fun getCredentialById(credentialId: String): CredentialEntity

    @Insert
    suspend fun saveCredentials(credentials: List<CredentialEntity>)

    @Query("DELETE FROM credentials")
    suspend fun deleteCredentials()
}