package com.example.favourr.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.favourr.FavourrModel
import com.example.favourr.databinding.ItemActiveFavourrBinding
import com.example.favourr.ui.home.ViewFavourActivity

class ActiveFavourrItemAdapter(private var favourrs: List<FavourrModel>) :
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

    fun setFavourrs(newFavourrs: List<FavourrModel>) {
        favourrs = newFavourrs
        notifyDataSetChanged()
    }

    inner class ActiveFavourrItemViewHolder(private val binding: ItemActiveFavourrBinding) : RecyclerView.ViewHolder(binding.root) {
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