package com.example.vault.ui.fragments.photos

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.RecoverableSecurityException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vault.R
import com.example.vault.adapters.ImagesRvAdapter
import com.example.vault.databinding.FragmentPhotosBinding
import com.example.vault.ui.fragments.BaseFragment
import com.example.vault.utils.Constants.DELETE_REQUEST_CODE
import com.example.vault.utils.Constants.IMAGE_PICK_REQUEST_CODE
import com.example.vault.utils.formatFileSize
import com.example.vault.viewmodels.VaultViewModel

class PhotosFragment : BaseFragment() {

    private val binding by lazy { FragmentPhotosBinding.inflate(layoutInflater) }

    private lateinit var imagesRvAdapter: ImagesRvAdapter

    private val viewModel: VaultViewModel by activityViewModels()

    private lateinit var globalUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupClickListeners()
        prepareRecyclerView()
    }

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
        navigationVisibility(true)

        viewModel.images.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.noImagesAdded.visibility = View.VISIBLE
                binding.noOfPhotos.visibility = View.GONE
                binding.imagesRv.visibility = View.GONE
            } else {
                binding.noImagesAdded.visibility = View.GONE
                binding.noOfPhotos.visibility = View.VISIBLE
                binding.imagesRv.visibility = View.VISIBLE
                val totalSize = formatFileSize(it.sumOf { image ->
                    image.imageSize
                })
                binding.noOfPhotos.text = "${it.size} Photos, $totalSize"
                imagesRvAdapter.differ.submitList(it)
            }
        }
    }

    private fun setupClickListeners() {
        binding.addImage.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, IMAGE_PICK_REQUEST_CODE)
        }
    }

    private fun prepareRecyclerView() {
        imagesRvAdapter = ImagesRvAdapter()

        binding.imagesRv.apply {
            adapter = imagesRvAdapter
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }

        imagesRvAdapter.onItemClick = {
            PhotoDetailsFragment.image = it
            navigateTo(R.id.action_photos_fragment_to_photoDetailsFragment)
        }
    }

    @SuppressLint("NewApi")
    private fun showDeleteFromGalleryDialog(imageUri: Uri) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.delete_original))
            .setMessage(getString(R.string.do_you_want_to_delete_the_original_from_your_camera_roll))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                try {
                    val rowsDeleted = requireContext().contentResolver.delete(imageUri, null, null)
                    if (rowsDeleted == 1) {
                        showToast(getString(R.string.image_deleted_successfully))
                    } else {
                        showToast(
                            getString(R.string.failed_to_delete_the_image)
                        )
                    }
                } catch (e: SecurityException) {
                    if (e is RecoverableSecurityException) {
                        // Use the intent from the exception to request permission to delete the file
                        val intentSender = e.userAction.actionIntent.intentSender
                        globalUri = imageUri
                        startIntentSenderForResult(
                            intentSender,
                            DELETE_REQUEST_CODE,
                            null,
                            0,
                            0,
                            0,
                            null
                        )
                    } else {
                        showToast(
                            getString(R.string.failed_to_delete_the_image_due_to_permission_issues)
                        )
                    }
                }
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            if (selectedImageUri != null) {
                val inputStream =
                    requireActivity().contentResolver.openInputStream(selectedImageUri)
                val byteArray = inputStream?.readBytes() ?: ByteArray(0)

                val cursor = requireActivity().contentResolver.query(
                    selectedImageUri,
                    arrayOf(OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE),
                    null,
                    null,
                    null
                )

                var fileNameSaved = "unknown"
                var fileSizeSaved = 0L

                if (cursor != null && cursor.moveToFirst()) {
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                    val fileName = cursor.getString(nameIndex)
                    val fileSize = cursor.getLong(sizeIndex)
                    cursor.close()

                    fileNameSaved = fileName
                    fileSizeSaved = fileSize
                }

                viewModel.insertImage(byteArray, fileNameSaved, fileSizeSaved)
                inputStream?.close()
                showDeleteFromGalleryDialog(selectedImageUri)
            }
        }

        if (requestCode == DELETE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                val rowsDeleted = requireContext().contentResolver.delete(globalUri, null, null)
                if (rowsDeleted == 1) {
                    showToast(
                        getString(R.string.image_deleted_successfully)
                    )
                } else {
                    showToast(
                        getString(R.string.failed_to_delete_the_image)
                    )
                }
            } catch (e: SecurityException) {
                showToast(
                    getString(R.string.failed_to_delete_the_image_due_to_permission_issues)
                )
            }
        }
    }
}