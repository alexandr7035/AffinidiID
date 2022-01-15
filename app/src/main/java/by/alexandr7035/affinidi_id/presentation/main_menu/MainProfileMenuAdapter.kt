package by.alexandr7035.affinidi_id.presentation.main_menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.alexandr7035.affinidi_id.databinding.ViewPrimaryMenuItemBinding

class MainProfileMenuAdapter(private val items: List<MenuItemModel>): RecyclerView.Adapter<MainProfileMenuAdapter.MenuItemViewHolder>() {

    inner class MenuItemViewHolder(val binding: ViewPrimaryMenuItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(itemModel: MenuItemModel) {
            binding.root.text = itemModel.title
            binding.root.setOnClickListener(itemModel.clickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val binding = ViewPrimaryMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}