package com.duyvv.kmadoc.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.duyvv.kmadoc.data.model.SettingsAction
import com.duyvv.kmadoc.databinding.ItemSettingsActionBinding

class SettingsActionAdapter(
    private val onClickItem: (Int, SettingsAction) -> Unit
) : ListAdapter<SettingsAction, SettingsActionAdapter.SettingsActionViewHolder>(
    object : DiffUtil.ItemCallback<SettingsAction>() {
        override fun areItemsTheSame(oldItem: SettingsAction, newItem: SettingsAction): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SettingsAction, newItem: SettingsAction): Boolean {
            return oldItem.title == newItem.title
        }
    }
) {

    inner class SettingsActionViewHolder(private val binding: ItemSettingsActionBinding) :
        ViewHolder(
            binding.root
        ) {
        fun bind(position: Int, item: SettingsAction) {
            binding.btnAction.apply {
                text = item.title
                setIconResource(item.iconRes)
                setOnClickListener {
                    onClickItem(position, item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsActionViewHolder {
        return SettingsActionViewHolder(
            ItemSettingsActionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SettingsActionViewHolder, position: Int) {
        holder.bind(position, getItem(position))
    }
}