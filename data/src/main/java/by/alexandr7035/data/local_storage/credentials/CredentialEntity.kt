package by.alexandr7035.data.local_storage.credentials

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credentials")
data class CredentialEntity(
    @PrimaryKey
    val credentialId: String
)