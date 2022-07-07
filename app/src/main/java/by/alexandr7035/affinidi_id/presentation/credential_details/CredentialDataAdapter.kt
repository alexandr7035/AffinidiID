package by.alexandr7035.affinidi_id.presentation.credential_details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import by.alexandr7035.affinidi_id.core.extensions.copyToClipboard
import by.alexandr7035.affinidi_id.core.extensions.showToast
import by.alexandr7035.affinidi_id.databinding.ViewVcDetailFieldBinding
import by.alexandr7035.affinidi_id.databinding.ViewVcDetailTitleOnlyBinding
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialFieldUi

class CredentialDataAdapter : RecyclerView.Adapter<CredentialDataAdapter.DataItemViewHolder>() {

    private var items: List<CredentialFieldUi> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<CredentialFieldUi>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CredentialFieldUi.Field -> {
                FIELD_ITEM_TYPE
            }

            is CredentialFieldUi.TitleOnly -> {
                TITLE_ITEM_TYPE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataItemViewHolder {
        return when (viewType) {
            FIELD_ITEM_TYPE -> {
                val binding = ViewVcDetailFieldBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DataItemViewHolder.FieldViewHolder(binding)
            }

            TITLE_ITEM_TYPE -> {
                val binding = ViewVcDetailTitleOnlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DataItemViewHolder.TitleOnlyViewHolder(binding)
            }

            else -> {
                throw NotImplementedError("viewType $viewType is not implemented")
            }
        }
    }

    override fun onBindViewHolder(holder: DataItemViewHolder, position: Int) {
        val dataItem = items[position]

        val holderParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
        holderParams.marginStart = dataItem.offsetLevel * OFFSET_IN_PIXELS
        holder.itemView.layoutParams = holderParams

        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    abstract class DataItemViewHolder(open val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(dataItem: CredentialFieldUi)

        class FieldViewHolder(override val binding: ViewVcDetailFieldBinding): DataItemViewHolder(binding) {
            override fun bind(dataItem: CredentialFieldUi) {
                val item = dataItem as CredentialFieldUi.Field

                binding.typeView.text = item.name
                binding.valueView.text = item.value

                binding.root.setOnClickListener {
                    binding.valueView.copyToClipboard(clipLabel = item.name)
                    binding.root.context.showToast(
                        "Copied \"${item.value}\""
                    )
                }
            }
        }

        class TitleOnlyViewHolder(override val binding: ViewVcDetailTitleOnlyBinding): DataItemViewHolder(binding) {
            override fun bind(dataItem: CredentialFieldUi) {
                val item = dataItem as CredentialFieldUi.TitleOnly
                binding.typeView.text = item.name
            }
        }

    }

    companion object {
        private const val FIELD_ITEM_TYPE = 1
        private const val TITLE_ITEM_TYPE = 2

        private const val OFFSET_IN_PIXELS = 56
    }
}