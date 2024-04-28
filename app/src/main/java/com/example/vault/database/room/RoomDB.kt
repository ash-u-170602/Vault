package com.example.vault.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [EncryptedImage::class, EncryptedFile::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RoomDB : RoomDatabase() {

    abstract fun getImageDao(): ImageDao
    abstract fun getFileDao(): FileDao

}