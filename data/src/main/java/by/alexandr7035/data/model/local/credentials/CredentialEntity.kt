package by.alexandr7035.data.model.local.credentials

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.CredentialSubject
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus

@Entity(tableName = "credentials")
data class CredentialEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rawVc: String
)