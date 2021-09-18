package com.example.favourr.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.favourr.databinding.ItemAvailableFavourrBinding

class AvailableFavourrItemAdapter(private val favourrs: List<Favourr>) :
    RecyclerView.Adapter<AvailableFavourrItemAdapter.AvailableFavourrItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AvailableFavourrItemViewHolder {
        val binding = ItemAvailableFavourrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AvailableFavourrItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AvailableFavourrItemViewHolder, position: Int) {
        holder.bind(favourrs[position])
    }

    override fun getItemCount(): Int = favourrs.size

    inner class AvailableFavourrItemViewHolder(binding: ItemAvailableFavourrBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favourr: Favourr) {

        }
    }
}