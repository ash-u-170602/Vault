package com.example.vault.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ImageDao {

    @Insert
    suspend fun insertImage(encryptedImage: EncryptedImage)

    @Query("SELECT * FROM images")
    fun getImages(): LiveData<List<EncryptedImage>>

    @Delete
    suspend fun deleteImage(encryptedImage: EncryptedImage)

    @Update
    suspend fun updateImage(image: EncryptedImage)

    @Query("SELECT * FROM images WHERE id = :id")
    suspend fun getImageById(id: Long): EncryptedImage?
}