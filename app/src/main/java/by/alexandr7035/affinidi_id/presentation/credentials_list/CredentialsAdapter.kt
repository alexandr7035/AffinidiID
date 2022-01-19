package by.alexandr7035.affinidi_id.presentation.credentials_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import by.alexandr7035.affinidi_id.databinding.ViewCredentialItemBinding
import by.alexandr7035.affinidi_id.databinding.ViewUnknownCredentialItemBinding
import by.alexandr7035.affinidi_id.presentation.credentials_list.vc_fields_recycler.VCFieldsAdapter

class CredentialsAdapter: RecyclerView.Adapter<CredentialsAdapter.CredentialViewHolder>() {

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
                CredentialViewHolder.NormalVCViewHolder(binding)
            }
            else -> {
                val binding = ViewUnknownCredentialItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CredentialViewHolder.UnknownVCViewHolder(binding)
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

    // FIXME not good
    fun getItemByPosition(position: Int): CredentialItemUiModel {
        return items[position]
    }

    abstract class CredentialViewHolder(open val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {

        abstract fun bind(item: CredentialItemUiModel)

        // FIXME not good
        fun getViewHolderPosition(): Int {
            return absoluteAdapterPosition
        }

        class NormalVCViewHolder(override val binding: ViewCredentialItemBinding): CredentialViewHolder(binding) {
            override fun bind(item: CredentialItemUiModel) {
                binding.credentialId.text = item.id
                binding.credentialTypeView.text = item.credentialTypeString
                binding.credentialExpires.text = item.expirationDate
                binding.statusLabel.text = item.credentialStatus
                binding.statusMark.setColorFilter(item.statusMarkColor)
                binding.fieldsRecycler.adapter = VCFieldsAdapter(item.vcFields)
            }
        }

        class UnknownVCViewHolder(override val binding: ViewUnknownCredentialItemBinding): CredentialViewHolder(binding) {
            override fun bind(item: CredentialItemUiModel) {
                binding.credentialId.text = item.id
                binding.credentialTypeView.text = item.credentialTypeString
                binding.credentialExpires.text = item.expirationDate
                binding.statusLabel.text = item.credentialStatus
                binding.statusMark.setColorFilter(item.statusMarkColor)
            }
        }
    }


    companion object {
        private const val NORMAL_CREDENTIAL = 1
        private const val UNKNOWN_CREDENTIAL = 2
    }
}