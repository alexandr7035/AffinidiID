package by.alexandr7035.affinidi_id.presentation.issue_credential

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.alexandr7035.affinidi_id.databinding.ViewAvailableCredentialBinding
import by.alexandr7035.affinidi_id.domain.model.credentials.available_credentials.AvailableCredentialModel

class AvailableCredentialsAdapter: RecyclerView.Adapter<AvailableCredentialsAdapter.ItemViewHolder>() {

    private var items: List<AvailableCredentialModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<AvailableCredentialModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ViewAvailableCredentialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder(private val binding: ViewAvailableCredentialBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AvailableCredentialModel) {
            binding.credentialTypeView.text = item.typeName
            binding.credentialDescriptionView.text = item.description
            binding.issuerView.text = item.issuer
        }
    }
}