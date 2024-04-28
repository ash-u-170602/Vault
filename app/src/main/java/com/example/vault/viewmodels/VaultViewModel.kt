package com.example.vault.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vault.aes_encryption.AESUtils
import com.example.vault.database.room.EncryptedFile
import com.example.vault.database.room.EncryptedImage
import com.example.vault.repositories.VaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class VaultViewModel @Inject constructor(private val repo: VaultRepository) : ViewModel() {

    fun insertImage(
        imageByte: ByteArray,
        fileNameSaved: String,
        fileSizeSaved: Long
    ) {
        viewModelScope.launch {
            repo.insertImage(
                EncryptedImage(
                    0,
                    AESUtils.encrypt(imageByte),
                    fileNameSaved,
                    fileSizeSaved,
                    System.currentTimeMillis(),
                    emptyList()
                )
            )
        }
    }

    val images: LiveData<List<EncryptedImage>> = repo.getImages()

    val files: LiveData<List<EncryptedFile>> = repo.getFiles()

    fun deleteImage(
        image: EncryptedImage
    ) {
        viewModelScope.launch {
            repo.deleteImage(image)
        }
    }

    fun addTimeStampToImageLogs(
        imageId: Long, timeStamp: Long
    ) {
        viewModelScope.launch {
            repo.addTimeStampToImageLogs(imageId, timeStamp)
        }
    }


    fun insertFile(
        fileByte: ByteArray,
        fileNameSaved: String,
        fileSizeSaved: Long
    ) {
        viewModelScope.launch {
            repo.insertFile(
                EncryptedFile(
                    0,
                    AESUtils.encrypt(fileByte),
                    fileNameSaved,
                    fileSizeSaved
                )
            )
        }
    }

    fun deleteFile(
        file: EncryptedFile
    ) {
        viewModelScope.launch {
            repo.deleteFile(file)
        }
    }
}