package by.alexandr7035.affinidi_id.presentation.credentials_list

import androidx.recyclerview.widget.DiffUtil
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card.CredentialCardUi

class VCsDiffUtilCallback(
    private val oldList: List<CredentialCardUi>,
    private val newList: List<CredentialCardUi>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // All other VC fields are static so skip them
        val idSame = oldList[oldItemPosition].id == newList[newItemPosition].id
        val statusSame = oldList[oldItemPosition].credentialStatusUi.statusText == newList[newItemPosition].credentialStatusUi.statusText

        return idSame && statusSame
    }
}