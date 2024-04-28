package com.example.vault.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FileDao {

    @Insert
    suspend fun insertFile(encryptedFile: EncryptedFile)

    @Query("SELECT * FROM files")
    fun getFiles(): LiveData<List<EncryptedFile>>

    @Delete
    suspend fun deleteFile(encryptedFile: EncryptedFile)

}