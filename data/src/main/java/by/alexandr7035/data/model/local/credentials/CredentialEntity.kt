package by.alexandr7035.data.model.local.credentials

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credentials")
data class CredentialEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val credentialId: String,
    val rawVc: String
)