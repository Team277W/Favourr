package com.example.favourr.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.favourr.ListIdData
import com.example.favourr.databinding.ItemActiveFavourrBinding
import com.example.favourr.models.FavourrModel
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
        val idx = (0..6).random()
        holder.bind(favourrs[position], idx)
    }

    override fun getItemCount(): Int = favourrs.size

    fun setFavourrs(newFavourrs: List<FavourrModel>?) {
        if (newFavourrs != null) {
            favourrs = newFavourrs
            notifyDataSetChanged()
        }
    }

    fun getFavourrs() : List<FavourrModel>{
        return favourrs
    }

    inner class ActiveFavourrItemViewHolder(private val binding: ItemActiveFavourrBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favourr: FavourrModel, icIndex: Int) {
            var idList = ListIdData().idList
            binding.userIcon.setImageResource(idList[icIndex])
            binding.name.text = favourr.title
            binding.price.text = String.format("$%.00f", favourr.cash)
            binding.root.setOnClickListener {
                val intent = Intent(it.context, ViewFavourActivity::class.java)
                intent.putExtra("FavourrData", favourr)
                intent.putExtra("icIndex", idList[icIndex])
                it.context.startActivity(intent)
            }
        }
    }
}