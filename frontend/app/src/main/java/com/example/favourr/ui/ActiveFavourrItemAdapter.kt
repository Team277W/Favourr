package com.example.favourr.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.favourr.Favourr
import com.example.favourr.databinding.ItemActiveFavourrBinding

class ActiveFavourrItemAdapter(private val favourrs: List<Favourr>) :
    RecyclerView.Adapter<ActiveFavourrItemAdapter.ActiveFavourrItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActiveFavourrItemViewHolder {
        val binding = ItemActiveFavourrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActiveFavourrItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActiveFavourrItemViewHolder, position: Int) {
        holder.bind(favourrs[position])
    }

    override fun getItemCount(): Int = favourrs.size

    inner class ActiveFavourrItemViewHolder(binding: ItemActiveFavourrBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favourr: Favourr) {

        }
    }
}