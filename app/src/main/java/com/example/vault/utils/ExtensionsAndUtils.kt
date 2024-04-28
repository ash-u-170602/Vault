package com.example.vault.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun formatFileSize(fileSize: Long): String {
    val kilobyte = 1024
    val megabyte = kilobyte * 1024
    val gigabyte = megabyte * 1024

    return when {
        fileSize < kilobyte -> "$fileSize bytes"
        fileSize < megabyte -> String.format("%.2f KB", fileSize / kilobyte.toFloat())
        fileSize < gigabyte -> String.format("%.2f MB", fileSize / megabyte.toFloat())
        else -> String.format("%.2f GB", fileSize / gigabyte.toFloat())
    }
}

@SuppressLint("SimpleDateFormat")
fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd MMMM yyyy (h:mma)")
    return formatter.format(Date(millis)).toLowerCase()
}