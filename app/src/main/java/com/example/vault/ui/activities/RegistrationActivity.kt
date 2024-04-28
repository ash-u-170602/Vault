package com.example.vault.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.vault.R
import com.example.vault.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRegistrationBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        if (true) {
            navController.navigateUp()
        } else {
            super.onBackPressed()
        }
    }
}