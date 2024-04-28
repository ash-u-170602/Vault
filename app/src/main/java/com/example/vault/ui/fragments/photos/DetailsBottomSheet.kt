package com.example.vault.ui.fragments.photos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vault.adapters.RecentlyOpenedRvAdapter
import com.example.vault.database.room.EncryptedImage
import com.example.vault.databinding.DetailsBottomSheetBinding
import com.example.vault.utils.convertMillisToDate
import com.example.vault.utils.formatFileSize
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailsBottomSheet(private val image: EncryptedImage) : BottomSheetDialogFragment() {

    private val binding by lazy { DetailsBottomSheetBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (image.image_logs.isEmpty()) {
            binding.recentlyOpenedTv.visibility = View.GONE
            binding.recentlyOpenedRv.visibility = View.GONE
        } else {
            binding.recentlyOpenedTv.visibility = View.VISIBLE
            binding.recentlyOpenedRv.visibility = View.VISIBLE
            binding.recentlyOpenedRv.apply {
                adapter = RecentlyOpenedRvAdapter(image.image_logs.reversed())
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }

        binding.name.text = "Name: ${image.imageName}"
        binding.size.text = "Size: ${formatFileSize(image.imageSize)}"
        binding.addedDate.text = "Added: ${convertMillisToDate(image.addedDate)}"


    }

}