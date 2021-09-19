package com.example.favourr.ui.localFavourrs

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.favourr.databinding.ItemLocalFavourrBinding
import com.example.favourr.models.FavourrModel
import com.example.favourr.ui.home.ViewFavourActivity

class LocalFavourrsAdapter(private var favourrs: List<FavourrModel>) :
    RecyclerView.Adapter<LocalFavourrsAdapter.LocalFavourrViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalFavourrViewHolder {
        val binding = ItemLocalFavourrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocalFavourrViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocalFavourrViewHolder, position: Int) {
        holder.bind(favourrs[position])
    }

    override fun getItemCount(): Int = favourrs.size

    fun setFavourrs(newFavourrs: List<FavourrModel>) {
        favourrs = newFavourrs
        notifyDataSetChanged()
    }

    inner class LocalFavourrViewHolder(private val binding: ItemLocalFavourrBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favourr: FavourrModel) {
            binding.name.text = favourr.title
            binding.price.text = "$" + favourr.cash.toString()
            binding.desc.text = favourr.body
            binding.root.setOnClickListener {
                val intent = Intent(it.context, ViewFavourActivity::class.java)
                intent.putExtra("FavourrData", favourr)
                it.context.startActivity(intent)
            }
        }
    }
}