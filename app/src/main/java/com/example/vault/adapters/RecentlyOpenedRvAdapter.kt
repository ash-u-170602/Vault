package com.example.vault.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vault.databinding.RecentlyOpenedRvItemBinding
import com.example.vault.utils.convertMillisToDate

class RecentlyOpenedRvAdapter(private val list: List<Long>) :
    RecyclerView.Adapter<RecentlyOpenedRvAdapter.RecentlyOpenedRvAdapterViewHolder>() {

    inner class RecentlyOpenedRvAdapterViewHolder(val binding: RecentlyOpenedRvItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentlyOpenedRvAdapterViewHolder {
        return RecentlyOpenedRvAdapterViewHolder(
            RecentlyOpenedRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount() = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecentlyOpenedRvAdapterViewHolder, position: Int) {

        holder.binding.recentlyOpened.text =
            "${position + 1}. ${convertMillisToDate(list[position])}"

    }


}