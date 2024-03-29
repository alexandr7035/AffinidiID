package by.alexandr7035.affinidi_id.presentation.credentials_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.databinding.ViewCredentialItemBinding
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card.CredentialCardUi

class CredentialsAdapter(private val credentialClickCallback: (credentialId: String) -> Unit) :
    RecyclerView.Adapter<CredentialsAdapter.CredentialViewHolder>() {

    private var items: List<CredentialCardUi> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CredentialViewHolder {
        val binding = ViewCredentialItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return when (viewType) {
            ACTIVE_CREDENTIAL -> {
                CredentialViewHolder.NormalVCViewHolder(binding, credentialClickCallback)
            }
            NON_ACTIVE_CREDENTIAL -> {
                CredentialViewHolder.ExpiredVCViewHolder(binding, credentialClickCallback)
            }

            else -> throw IllegalStateException("View type not implemented")
        }
    }

    override fun onBindViewHolder(holder: CredentialViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].credentialStatusUi.domainStatus) {
            CredentialStatus.Active -> ACTIVE_CREDENTIAL
            CredentialStatus.Expired -> NON_ACTIVE_CREDENTIAL
        }
    }

    fun setItems(items: List<CredentialCardUi>) {
        val diffUtilCallback = VCsDiffUtilCallback(oldList = this.items, newList = items)
        val diff = DiffUtil.calculateDiff(diffUtilCallback)
        this.items = items
        diff.dispatchUpdatesTo(this)
    }

    abstract class CredentialViewHolder(open val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        abstract fun bind(item: CredentialCardUi)

        class NormalVCViewHolder(
            override val binding: ViewCredentialItemBinding,
            private val credentialClickCallback: (credentialId: String) -> Unit
        ) : CredentialViewHolder(binding) {
            override fun bind(item: CredentialCardUi) {
                binding.credentialId.text = item.id
                binding.credentialTypeView.text = item.credentialTypeText
                binding.issuanceDate.text = item.issuanceDateText
                binding.root.setOnClickListener {
                    credentialClickCallback.invoke(item.id)
                }
            }
        }


        class ExpiredVCViewHolder(
            override val binding: ViewCredentialItemBinding,
            private val credentialClickCallback: (credentialId: String) -> Unit
        ) : CredentialViewHolder(binding) {
            override fun bind(item: CredentialCardUi) {
                binding.credentialId.text = item.id
                binding.credentialTypeView.text = item.credentialTypeText
                binding.issuanceDate.text = item.issuanceDateText

                binding.root.setOnClickListener {
                    credentialClickCallback.invoke(item.id)
                }

                // Set other background
                binding.root.background = ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.background_credential_item_secondary
                )
            }
        }

    }

    companion object {
        private const val ACTIVE_CREDENTIAL = 1
        private const val NON_ACTIVE_CREDENTIAL = 2
    }
}