package com.example.vault.ui.fragments.files

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vault.R
import com.example.vault.adapters.FilesRvAdapter
import com.example.vault.databinding.FragmentFilesBinding
import com.example.vault.ui.fragments.BaseFragment
import com.example.vault.utils.Constants.PDF_PICK_REQUEST_CODE
import com.example.vault.utils.formatFileSize
import com.example.vault.viewmodels.VaultViewModel

class FilesFragment : BaseFragment() {

    private val binding by lazy { FragmentFilesBinding.inflate(layoutInflater) }

    private val viewModel: VaultViewModel by activityViewModels()

    private lateinit var filesRvAdapter: FilesRvAdapter

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

        viewModel.files.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.noFilesAdded.visibility = View.VISIBLE
                binding.noOfPhotos.visibility = View.GONE
                binding.filesRv.visibility = View.GONE
            } else {
                binding.noFilesAdded.visibility = View.GONE
                binding.noOfPhotos.visibility = View.VISIBLE
                binding.filesRv.visibility = View.VISIBLE
                val totalSize = formatFileSize(it.sumOf { file ->
                    file.fileSize
                })
                binding.noOfPhotos.text = "${it.size} Files, $totalSize"
                filesRvAdapter.differ.submitList(it)
            }
        }
    }

    private fun prepareRecyclerView() {
        filesRvAdapter = FilesRvAdapter()

        binding.filesRv.apply {
            adapter = filesRvAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }

        filesRvAdapter.onItemClick = {
            FilesDetailsFragment.file = it
            navigateTo(R.id.action_files_fragment_to_filesDetailsFragment)
        }
    }

    private fun setupClickListeners() {
        binding.addFile.setOnClickListener {
            val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
            pdfIntent.type = "application/pdf"
            startActivityForResult(pdfIntent, PDF_PICK_REQUEST_CODE)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PDF_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedPdfUri = data.data
            if (selectedPdfUri != null) {
                val inputStream =
                    requireActivity().contentResolver.openInputStream(selectedPdfUri)
                val byteArray = inputStream?.readBytes() ?: ByteArray(0)

                val cursor = requireActivity().contentResolver.query(
                    selectedPdfUri,
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

                if (fileSizeSaved > 5 * 1024 * 1024) {
                    showToast(getString(R.string.file_too_large))
                } else {
                    viewModel.insertFile(byteArray, fileNameSaved, fileSizeSaved)
                }
                inputStream?.close()
            }
        }
    }
}