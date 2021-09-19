package com.example.favourr.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.favourr.FavourrModel
import com.example.favourr.databinding.ItemAvailableFavourrBinding
import com.example.favourr.ui.home.ViewFavourActivity

class AvailableFavourrItemAdapter(private var favourrs: List<FavourrModel>) :
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

    fun setFavourrs(newFavourrs: List<FavourrModel>) {
        favourrs = newFavourrs
        notifyDataSetChanged()
    }

    inner class AvailableFavourrItemViewHolder(private val binding: ItemAvailableFavourrBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favourr: FavourrModel) {
            binding.name.text = favourr.title
            binding.price.text = "$" + favourr.cash.toString()
            binding.root.setOnClickListener {
                val intent = Intent(it.context, ViewFavourActivity::class.java)
                intent.putExtra("FavourrData", favourr)
                it.context.startActivity(intent)
            }
        }
    }
}