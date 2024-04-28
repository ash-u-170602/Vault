package com.example.vault.ui.fragments.files

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.vault.R
import com.example.vault.aes_encryption.AESUtils
import com.example.vault.database.room.EncryptedFile
import com.example.vault.databinding.FragmentFileDetailsBinding
import com.example.vault.ui.fragments.BaseFragment
import com.example.vault.viewmodels.VaultViewModel

class FilesDetailsFragment : BaseFragment() {

    companion object {
        lateinit var file: EncryptedFile
    }

    private val binding by lazy { FragmentFileDetailsBinding.inflate(layoutInflater) }

    private val viewModel: VaultViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.pdfViewer.fromBytes(AESUtils.decrypt(file.fileData))
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .defaultPage(0)
            .enableAnnotationRendering(true)
            .password(null)
            .scrollHandle(null)
            .load()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationVisibility(false)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.menu.setOnClickListener {
            showPopupMenu()
        }

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(requireContext(), binding.menu)
        popupMenu.inflate(R.menu.menu_dropdown_file)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete_image -> {
                    viewModel.deleteFile(file)
                    findNavController().navigateUp()
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

}