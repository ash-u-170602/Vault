package com.example.vault.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vault.database.room.EncryptedFile
import com.example.vault.databinding.FilesRvItemBinding

class FilesRvAdapter :
    RecyclerView.Adapter<FilesRvAdapter.FilesRvAdapterViewHolder>() {

    inner class FilesRvAdapterViewHolder(val binding: FilesRvItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    lateinit var onItemClick: ((EncryptedFile) -> Unit)

    private val diffUtil = object : DiffUtil.ItemCallback<EncryptedFile>() {
        override fun areItemsTheSame(oldItem: EncryptedFile, newItem: EncryptedFile): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EncryptedFile, newItem: EncryptedFile): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilesRvAdapterViewHolder {
        return FilesRvAdapterViewHolder(
            FilesRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: FilesRvAdapterViewHolder, position: Int) {

        val file = differ.currentList[position]

        holder.binding.name.text = file.fileName

        holder.itemView.setOnClickListener {
            onItemClick.invoke(file)
        }
    }


}