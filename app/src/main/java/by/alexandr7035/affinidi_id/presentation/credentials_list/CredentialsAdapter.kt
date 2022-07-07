package by.alexandr7035.affinidi_id.presentation.credentials_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.databinding.ViewCredentialItemBinding
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card.CredentialCardUi
import java.lang.IllegalStateException

class CredentialsAdapter(private val credentialClickListener: CredentialClickListener) :
    RecyclerView.Adapter<CredentialsAdapter.CredentialViewHolder>() {

    private var items: List<CredentialCardUi> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CredentialViewHolder {
        val binding = ViewCredentialItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return when (viewType) {
            ACTIVE_CREDENTIAL -> {
                CredentialViewHolder.NormalVCViewHolder(binding, credentialClickListener)
            }
            NON_ACTIVE_CREDENTIAL -> {
                CredentialViewHolder.ExpiredVCViewHolder(binding, credentialClickListener)
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
        return if (items[position].credentialStatus == CredentialStatus.ACTIVE) {
            ACTIVE_CREDENTIAL
        }
        else {
            NON_ACTIVE_CREDENTIAL
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<CredentialCardUi>) {
        this.items = items
        notifyDataSetChanged()
    }

    abstract class CredentialViewHolder(open val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        abstract fun bind(item: CredentialCardUi)

        class NormalVCViewHolder(
            override val binding: ViewCredentialItemBinding,
            private val credentialClickListener: CredentialClickListener
        ) : CredentialViewHolder(binding) {
            override fun bind(item: CredentialCardUi) {
                binding.credentialId.text = item.id
                binding.credentialTypeView.text = item.credentialTypeText
                binding.credentialExpires.text = item.credentialStatusText

                binding.root.setOnClickListener {
                    credentialClickListener.onCredentialClicked(item.id)
                }
            }
        }


        class ExpiredVCViewHolder(
            override val binding: ViewCredentialItemBinding,
            private val credentialClickListener: CredentialClickListener
        ) : CredentialViewHolder(binding) {
            override fun bind(item: CredentialCardUi) {
                binding.credentialId.text = item.id
                binding.credentialTypeView.text = item.credentialTypeText
                binding.credentialExpires.text = item.credentialStatusText

                binding.root.setOnClickListener {
                    credentialClickListener.onCredentialClicked(item.id)
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