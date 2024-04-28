package com.example.vault.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class EncryptedImage(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "image_data") val imageData: ByteArray,
    @ColumnInfo(name = "image_name") val imageName: String,
    @ColumnInfo(name = "image_size") val imageSize: Long,
    @ColumnInfo(name = "added_date") val addedDate: Long,
    @ColumnInfo(name = "image_logs") val image_logs: List<Long>,
)