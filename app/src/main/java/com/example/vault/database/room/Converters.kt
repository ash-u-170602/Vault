package com.example.vault.database.room

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromTimestampList(list: List<Long>?): String {
        return list?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun toTimestampList(data: String): List<Long> {
        if (data.isEmpty()) return listOf()
        return data.split(",").map { it.toLong() }
    }
}
