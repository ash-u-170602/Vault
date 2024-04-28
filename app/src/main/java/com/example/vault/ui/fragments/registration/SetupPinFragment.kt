package com.example.vault.ui.fragments.registration

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vault.R
import com.example.vault.database.SharedService
import com.example.vault.databinding.FragmentSetupPinBinding
import com.example.vault.ui.activities.DashboardActivity
import com.example.vault.ui.fragments.BaseFragment

class SetupPinFragment : BaseFragment() {

    private val binding by lazy { FragmentSetupPinBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.register.setOnClickListener {
            register()
        }
    }

    private fun register() {
        if (binding.passcode.text.toString().length != 6) {
            showToast(getString(R.string.enter_valid_passcode))
            return
        }

        if (binding.confirmPasscode.text.toString().length != 6) {
            showToast(getString(R.string.enter_valid_passcode))
            return
        }

        if (binding.confirmPasscode.text.toString() != binding.passcode.text.toString()) {
            showToast(getString(R.string.security_code_doesnot_matches))
            return
        }

        SharedService.security_code = binding.confirmPasscode.text.toString()
        openDashboard()

    }

    private fun openDashboard() {
        val intent = Intent(requireContext(), DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}