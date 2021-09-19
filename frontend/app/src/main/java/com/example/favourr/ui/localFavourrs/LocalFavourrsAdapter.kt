package com.example.favourr.ui.localFavourrs

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.favourr.ListIdData
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
        val idx = (0..6).random()
        holder.bind(favourrs[position], idx)
    }

    override fun getItemCount(): Int = favourrs.size

    fun setFavourrs(newFavourrs: List<FavourrModel>) {
        favourrs = newFavourrs
        notifyDataSetChanged()
    }
    fun getFlavourrs() : List<FavourrModel> {
        return favourrs
    }
    inner class LocalFavourrViewHolder(private val binding: ItemLocalFavourrBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favourr: FavourrModel, icIndex: Int) {
            val idList = ListIdData().idList
            binding.userIcon.setImageResource(idList[icIndex])
            binding.name.text = favourr.title
            binding.price.text = "$" + favourr.cash.toString()
            binding.desc.text = favourr.body
            binding.location.text = favourr.city.capitalize()
            binding.root.setOnClickListener {
                val intent = Intent(it.context, ViewFavourActivity::class.java)
                intent.putExtra("FavourrData", favourr)
                intent.putExtra("icIndex", icIndex)
                it.context.startActivity(intent)
            }
        }
    }
}