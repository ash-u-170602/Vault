package com.example.vault.ui.fragments.registration

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vault.R
import com.example.vault.database.SharedService
import com.example.vault.databinding.FragmentSplashBinding
import com.example.vault.ui.activities.OnboardingActivity
import com.example.vault.ui.fragments.BaseFragment

class SplashFragment : BaseFragment() {

    private val binding by lazy { FragmentSplashBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            if (SharedService.security_code == null) {
                navigateTo(R.id.action_splashFragment_to_setupPinFragment)
            } else {
                openOnboarding()
            }
        }, 2000)

    }

    private fun openOnboarding() {
        val intent = Intent(requireContext(), OnboardingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}