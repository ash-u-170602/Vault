package com.example.vault.database

import android.content.Context
import android.content.SharedPreferences
import com.example.vault.VaultApplication

object SharedService {

    private const val PREF_NAME = "vault_pref"
    private const val KEY_SECURITY_CODE = "key_security_code"

    private val context by lazy { VaultApplication.instance?.baseContext }
    private val sharedPreferences: SharedPreferences =
        context!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var security_code: String?
        get() = sharedPreferences.getString(KEY_SECURITY_CODE, null)
        set(value) {
            sharedPreferences.edit().putString(KEY_SECURITY_CODE, value).apply()
        }

}