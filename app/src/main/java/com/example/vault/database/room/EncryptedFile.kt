package com.example.vault.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "files")
data class EncryptedFile(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "file_data") val fileData: ByteArray,
    @ColumnInfo(name = "file_name") val fileName: String,
    @ColumnInfo(name = "file_size") val fileSize: Long,
)