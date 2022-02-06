package by.alexandr7035.affinidi_id.presentation.issue_credential

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.alexandr7035.affinidi_id.databinding.ViewAvailableCredentialBinding
import by.alexandr7035.affinidi_id.domain.model.credentials.available_credential_types.AvailableCredentialTypeModel

class AvailableCredentialsAdapter(private val credentialClickListener: CredentialClickListener): RecyclerView.Adapter<AvailableCredentialsAdapter.ItemViewHolder>() {

    private var items: List<AvailableCredentialTypeModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<AvailableCredentialTypeModel>) {
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
        fun bind(item: AvailableCredentialTypeModel) {
            binding.credentialTypeView.text = item.typeName
            binding.credentialDescriptionView.text = item.description
            binding.issuerView.text = item.issuer

            binding.root.setOnClickListener {
                credentialClickListener.onClick(item.issuingCredentialType)
            }
        }
    }
}