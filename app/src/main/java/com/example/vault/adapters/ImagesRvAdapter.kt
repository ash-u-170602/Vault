package com.example.vault.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vault.aes_encryption.AESUtils
import com.example.vault.database.room.EncryptedImage
import com.example.vault.databinding.ImagesRvItemBinding

class ImagesRvAdapter :
    RecyclerView.Adapter<ImagesRvAdapter.ImagesRvAdapterViewHolder>() {

    inner class ImagesRvAdapterViewHolder(val binding: ImagesRvItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    lateinit var onItemClick: ((EncryptedImage) -> Unit)

    private val diffUtil = object : DiffUtil.ItemCallback<EncryptedImage>() {
        override fun areItemsTheSame(oldItem: EncryptedImage, newItem: EncryptedImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EncryptedImage, newItem: EncryptedImage): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImagesRvAdapterViewHolder {
        return ImagesRvAdapterViewHolder(
            ImagesRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ImagesRvAdapterViewHolder, position: Int) {

        val images = differ.currentList[position]

        Glide.with(holder.itemView.context).load(AESUtils.decrypt(images.imageData))
            .into(holder.binding.image)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(images)
        }
    }


}