package by.alexandr7035.affinidi_id.presentation.credentials_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.alexandr7035.affinidi_id.databinding.ViewCredentialItemBinding

class CredentialsAdapter: RecyclerView.Adapter<CredentialsAdapter.CredentialViewHolder>() {

    private var items: List<CredentialItemUiModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CredentialViewHolder {
        val binding = ViewCredentialItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CredentialViewHolder(binding)
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

    inner class CredentialViewHolder(val binding: ViewCredentialItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CredentialItemUiModel) {
            binding.credentialTypeView.text = item.credentialType
            binding.credentialExpires.text = item.expirationDate
            binding.statusLabel.text = item.credentialStatus
            binding.statusMark.setColorFilter(item.statusMarkColor)
        }
    }

}