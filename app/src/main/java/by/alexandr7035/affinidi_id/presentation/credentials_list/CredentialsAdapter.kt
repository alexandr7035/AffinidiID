package by.alexandr7035.affinidi_id.presentation.credentials_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import by.alexandr7035.affinidi_id.databinding.ViewCredentialItemBinding
import by.alexandr7035.affinidi_id.databinding.ViewUnknownCredentialItemBinding

class CredentialsAdapter(private val credentialClickListener: CredentialClickListener): RecyclerView.Adapter<CredentialsAdapter.CredentialViewHolder>() {

    private var items: List<CredentialItemUiModel> = emptyList()

    override fun getItemViewType(position: Int): Int {
        return if (items[position].isUnknownType) {
            UNKNOWN_CREDENTIAL
        }
        else {
            NORMAL_CREDENTIAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CredentialViewHolder {
        return when (viewType) {
            NORMAL_CREDENTIAL -> {
                val binding = ViewCredentialItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CredentialViewHolder.NormalVCViewHolder(binding, credentialClickListener)
            }
            else -> {
                val binding = ViewUnknownCredentialItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CredentialViewHolder.UnknownVCViewHolder(binding, credentialClickListener)
            }
        }
    }

    override fun onBindViewHolder(holder: CredentialViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<CredentialItemUiModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    abstract class CredentialViewHolder(open val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {

        abstract fun bind(item: CredentialItemUiModel)

        class NormalVCViewHolder(override val binding: ViewCredentialItemBinding, private val credentialClickListener: CredentialClickListener): CredentialViewHolder(binding) {
            override fun bind(item: CredentialItemUiModel) {
                binding.credentialId.text = item.id
                binding.credentialTypeView.text = item.credentialTypeString
                binding.credentialExpires.text = item.expirationDate
                binding.statusLabel.text = item.credentialStatus.status
                binding.statusMark.setColorFilter(item.credentialStatus.statusColor)

                binding.root.setOnClickListener {
                    credentialClickListener.onCredentialClicked(item.id)
                }
            }
        }

        class UnknownVCViewHolder(override val binding: ViewUnknownCredentialItemBinding, private val credentialClickListener: CredentialClickListener): CredentialViewHolder(binding) {
            override fun bind(item: CredentialItemUiModel) {
                binding.credentialId.text = item.id
                binding.credentialTypeView.text = item.credentialTypeString
                binding.credentialExpires.text = item.expirationDate
                binding.statusLabel.text = item.credentialStatus.status
                binding.statusMark.setColorFilter(item.credentialStatus.statusColor)

                binding.root.setOnClickListener {
                    credentialClickListener.onCredentialClicked(item.id)
                }
            }
        }
    }


    companion object {
        private const val NORMAL_CREDENTIAL = 1
        private const val UNKNOWN_CREDENTIAL = 2
    }
}