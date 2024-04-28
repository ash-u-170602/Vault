package com.example.vault.ui.fragments

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vault.ui.activities.DashboardActivity
import com.example.vault.utils.showToast

open class BaseFragment : Fragment() {

    fun navigateTo(destinationId: Int) {
        findNavController().navigate(destinationId)
    }

    fun showToast(message: String) {
        requireContext().showToast(message)
    }

    fun navigationVisibility(isVisible: Boolean) {
        (activity as DashboardActivity).navigationVisibility(isVisible)
    }

}