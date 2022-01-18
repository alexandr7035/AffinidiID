package by.alexandr7035.affinidi_id.presentation.credentials_list.vc_fields_recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.alexandr7035.affinidi_id.databinding.ViewVcFieldBinding

class VCFieldsAdapter(private val fields: List<FieldItem>): RecyclerView.Adapter<VCFieldsAdapter.FieldViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
        val binding = ViewVcFieldBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FieldViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FieldViewHolder, position: Int) {
        holder.bind(fields[position])
    }

    override fun getItemCount(): Int {
        return fields.size
    }

    inner class FieldViewHolder(private val binding: ViewVcFieldBinding): RecyclerView.ViewHolder(binding.root) {
        fun  bind(item: FieldItem) {
            binding.typeView.text = item.type
            binding.valueView.text = item.value
        }
    }
}