package com.example.vault.ui.fragments.photos

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.vault.R
import com.example.vault.aes_encryption.AESUtils
import com.example.vault.database.room.EncryptedImage
import com.example.vault.databinding.FragmentPhotoDetailsBinding
import com.example.vault.ui.fragments.BaseFragment
import com.example.vault.viewmodels.VaultViewModel

class PhotoDetailsFragment : BaseFragment() {

    companion object {
        lateinit var image: EncryptedImage
    }

    private val binding by lazy { FragmentPhotoDetailsBinding.inflate(layoutInflater) }

    private val viewModel: VaultViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Glide.with(requireContext()).load(AESUtils.decrypt(image.imageData)).into(binding.image)
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
        viewModel.addTimeStampToImageLogs(image.id, System.currentTimeMillis())
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
        popupMenu.inflate(R.menu.menu_dropdown)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete_image -> {
                    viewModel.deleteImage(image)
                    findNavController().navigateUp()
                    true
                }

                R.id.restore_image -> {
                    restoreImageToGallery(AESUtils.decrypt(image.imageData))
                    showToast(getString(R.string.image_restored_to_gallery))
                    viewModel.deleteImage(image)
                    findNavController().navigateUp()
                    true
                }

                R.id.share_image -> {
                    shareImage(AESUtils.decrypt(image.imageData))
                    true
                }

                R.id.image_details -> {
                    val detailsBottomSheet = DetailsBottomSheet(image)
                    detailsBottomSheet.show(parentFragmentManager, "detailsBottomSheet")
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    private fun shareImage(imageByteArray: ByteArray) {
        // Create a temporary file to store the image
        val tempFile = java.io.File.createTempFile(
            image.imageName.removeSuffix(".png"),
            ".png",
            requireContext().cacheDir
        )

        // Write the image bytes to the temporary file
        tempFile.writeBytes(imageByteArray)

        // Get URI for the file using FileProvider
        val fileUri: Uri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            tempFile
        )

        // Create an intent to share the image
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, fileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        // Start the activity with the share intent
        startActivity(Intent.createChooser(intent, "Share Image"))
    }


    private fun restoreImageToGallery(imageByteArray: ByteArray) {
        val filename = image.imageName

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
        }

        val resolver = requireContext().contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        imageUri?.let { uri ->
            resolver.openOutputStream(uri)?.use { outputStream ->
                outputStream.write(imageByteArray)
            }
        }
    }
}