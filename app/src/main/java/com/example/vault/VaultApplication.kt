package com.example.vault

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VaultApplication : Application() {

    companion object {
        @JvmStatic
        var instance: VaultApplication? = null
    }

    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //Disable dark mode
        instance = this

        super.onCreate()
    }

}