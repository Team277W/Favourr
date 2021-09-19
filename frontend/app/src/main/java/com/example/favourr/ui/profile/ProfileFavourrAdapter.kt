package com.example.favourr.ui.profile

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.favourr.databinding.ItemLocalFavourrBinding
import com.example.favourr.models.FavourrModel
import com.example.favourr.ui.home.ViewFavourActivity

class ProfileFavourrAdapter(private var favourrs: List<FavourrModel>) :
    RecyclerView.Adapter<ProfileFavourrAdapter.ProfileFavourrViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileFavourrViewHolder {
        val binding = ItemLocalFavourrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileFavourrViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileFavourrViewHolder, position: Int) {
        val idx = (0..6).random()
        holder.bind(favourrs[position], idx)
    }

    override fun getItemCount(): Int = favourrs.size

    fun setFavourrs(newFavourrs: List<FavourrModel>) {
        favourrs = newFavourrs
        notifyDataSetChanged()
    }

    inner class ProfileFavourrViewHolder(private val binding: ItemLocalFavourrBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favourr: FavourrModel, icIndex: Int) {
            binding.name.text = favourr.title
            binding.price.text = String.format("$%.00f", favourr.cash)
            binding.desc.text = favourr.body
            binding.root.setOnClickListener {
                val intent = Intent(it.context, ViewFavourActivity::class.java)
                intent.putExtra("FavourrData", favourr)
                intent.putExtra("isCreator", true)
                it.context.startActivity(intent)
            }
        }
    }
}