package by.alexandr7035.data.local_storage.credentials

import android.net.wifi.hotspot2.pps.Credential
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CredentialsDAO {
    @Query("SELECT * FROM credentials")
    suspend fun getCredentials(): List<CredentialEntity>

    @Insert
    suspend fun saveCredentials(credentials: List<CredentialEntity>)

    @Query("DELETE FROM credentials")
    suspend fun deleteCredentials()
}