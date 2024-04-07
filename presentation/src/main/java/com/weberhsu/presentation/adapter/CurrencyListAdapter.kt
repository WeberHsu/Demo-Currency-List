package com.weberhsu.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.weberhsu.domain.entity.CurrencyInfo
import com.weberhsu.presentation.databinding.ItemCurrencyBinding

/**
 * author : weber
 * desc : Adapter for currencyinfo list
 */
class CurrencyListAdapter : RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder>() {

    private var asyncListDiff: AsyncListDiffer<CurrencyInfo>

    init {
        asyncListDiff = AsyncListDiffer(this, DiffCallback())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding = ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return asyncListDiff.currentList.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = asyncListDiff.currentList[position]
        holder.bind(item)
    }

    class CurrencyViewHolder(private val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currencyInfo: CurrencyInfo) {
            binding.tvName.text = currencyInfo.name
            binding.tvIcon.text = currencyInfo.name.first().toString()
            binding.tvCode.text = currencyInfo.code
        }
    }

    fun refreshData(list: List<CurrencyInfo>) {
        asyncListDiff.submitList(list)
    }

    private class DiffCallback : DiffUtil.ItemCallback<CurrencyInfo>() {
        override fun areItemsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
            return oldItem == newItem
        }
    }
}