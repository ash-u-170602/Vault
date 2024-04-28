package com.example.vault.repositories

import androidx.lifecycle.LiveData
import com.example.vault.database.room.EncryptedFile
import com.example.vault.database.room.EncryptedImage
import com.example.vault.database.room.RoomDB
import javax.inject.Inject

class VaultRepository @Inject constructor(private val roomDB: RoomDB) {

    suspend fun insertImage(
        image: EncryptedImage
    ) {
        roomDB.getImageDao().insertImage(image)
    }

    fun getImages(): LiveData<List<EncryptedImage>> {
        return roomDB.getImageDao().getImages()
    }

    suspend fun deleteImage(
        image: EncryptedImage
    ) {
        roomDB.getImageDao().deleteImage(image)
    }

    suspend fun addTimeStampToImageLogs(imageId: Long, timeStamp: Long) {
        val image = roomDB.getImageDao().getImageById(imageId)
        image?.let {
            val updatedLogs = it.image_logs.toMutableList()
            updatedLogs.add(timeStamp)
            val updatedImage = it.copy(image_logs = updatedLogs)
            roomDB.getImageDao().updateImage(updatedImage)
        }
    }


    fun getFiles(): LiveData<List<EncryptedFile>> {
        return roomDB.getFileDao().getFiles()
    }

    suspend fun insertFile(
        file: EncryptedFile
    ) {
        roomDB.getFileDao().insertFile(file)
    }

    suspend fun deleteFile(
        file: EncryptedFile
    ) {
        roomDB.getFileDao().deleteFile(file)
    }
}