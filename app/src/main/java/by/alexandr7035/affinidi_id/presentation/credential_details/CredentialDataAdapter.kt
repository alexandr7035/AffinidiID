package by.alexandr7035.affinidi_id.presentation.credential_details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import by.alexandr7035.affinidi_id.databinding.LayoutProgressViewBinding
import by.alexandr7035.affinidi_id.databinding.ViewVcDetailFieldBinding
import by.alexandr7035.affinidi_id.databinding.ViewVcDetailSpacingBinding

class CredentialDataAdapter(): RecyclerView.Adapter<CredentialDataAdapter.DataItemViewHolder>() {

    private var items: List<CredentialDataItem> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<CredentialDataItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CredentialDataItem.Field -> {
                FIELD_ITEM_TYPE
            }
            is CredentialDataItem.Spacing -> {
                SPACING_ITEM_TYPE
            }
            is CredentialDataItem.Loading -> {
                LOADING_ITEM_TYPE
            }
            else -> {
                throw RuntimeException("VC details: unknown viewType")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataItemViewHolder {
        return when (viewType) {
            FIELD_ITEM_TYPE -> {
                val binding = ViewVcDetailFieldBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DataItemViewHolder.FieldViewHolder(binding)
            }
            LOADING_ITEM_TYPE -> {
                val binding = LayoutProgressViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DataItemViewHolder.LoadingViewHolder(binding)
            }
            else -> {
                val binding = ViewVcDetailSpacingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DataItemViewHolder.SpacingViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: DataItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    abstract class DataItemViewHolder(open val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(dataItem: CredentialDataItem)

        class FieldViewHolder(override val binding: ViewVcDetailFieldBinding): DataItemViewHolder(binding) {
            override fun bind(dataItem: CredentialDataItem) {
                val item = dataItem as CredentialDataItem.Field

                binding.typeView.text = item.name
                binding.valueView.text = item.value
            }
        }

        class SpacingViewHolder(override val binding: ViewVcDetailSpacingBinding): DataItemViewHolder(binding) {
            override fun bind(dataItem: CredentialDataItem) {

            }
        }

        class LoadingViewHolder(override val binding: LayoutProgressViewBinding): DataItemViewHolder(binding) {
            override fun bind(dataItem: CredentialDataItem) {

            }
        }
    }

    companion object {
        private const val FIELD_ITEM_TYPE = 1
        private const val SPACING_ITEM_TYPE = 2
        private const val LOADING_ITEM_TYPE = 3
    }
}