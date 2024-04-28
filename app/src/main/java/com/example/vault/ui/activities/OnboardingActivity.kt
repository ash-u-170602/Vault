package com.example.vault.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.vault.R
import com.example.vault.biometrics.BiometricPromptManager
import com.example.vault.database.SharedService
import com.example.vault.databinding.ActivityOboardingBinding
import com.example.vault.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {

    private val binding by lazy { ActivityOboardingBinding.inflate(layoutInflater) }

    private val promptManager by lazy { BiometricPromptManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        promptManager.showBiometricPrompt(
            getString(R.string.unlock_vault),
            getString(R.string.touch_your_fingerprint_sensor)
        )

        lifecycleScope.launch {
            promptManager.promptResults.collect { result ->
                handleBiometricResult(result)
            }
        }

        binding.fingerprint.setOnClickListener {
            promptManager.showBiometricPrompt(
                getString(R.string.unlock_vault),
                getString(R.string.touch_your_fingerprint_sensor)
            )
        }

        binding.passcode.setOtpCompletionListener {
            if (it == SharedService.security_code) {
                openDashboard()
            } else {
                showToast(getString(R.string.incorrect_security_code))
            }
        }
    }

    private fun handleBiometricResult(result: BiometricPromptManager.BiometricResult) {
        when (result) {
            is BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
//                Toast.makeText(this, "Authentication succeeded", Toast.LENGTH_LONG).show()
                openDashboard()
            }

            is BiometricPromptManager.BiometricResult.AuthenticationError -> {
//                Toast.makeText(this, "Authentication error: ${result.error}", Toast.LENGTH_LONG)
//                    .show()
            }

            is BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
//                Toast.makeText(this, "Authentication failed", Toast.LENGTH_LONG).show()
            }

            is BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
//                Toast.makeText(this, "Biometric hardware unavailable", Toast.LENGTH_LONG).show()
            }

            is BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
//                Toast.makeText(this, "Biometric feature not available", Toast.LENGTH_LONG).show()
            }

            is BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                Toast.makeText(this, "No fingerprints enrolled", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun openDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}