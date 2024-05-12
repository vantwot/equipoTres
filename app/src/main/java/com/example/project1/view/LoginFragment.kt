package com.example.project1.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project1.R
import com.example.project1.databinding.FragmentLoginBinding
import com.example.project1.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()


    private val biometricLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Log.e("MY_APP_TAG", "Correct")
        } else {
            Log.e("MY_APP_TAG", "Error")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initBiometricLauncher(biometricLauncher)
        viewModel.initBiometricPrompt(this, object : LoginViewModel.BiometricAuthenticationCallback {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                Toast.makeText(requireContext(), "Error de autenticación: $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded() {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }

            override fun onAuthenticationFailed() {
                Toast.makeText(requireContext(), getString(R.string.autenticaci_n_fallida), Toast.LENGTH_SHORT).show()
            }
        })

        binding.imgFinger.setOnClickListener {
            viewModel.handleFingerClick(this)
        }
    }


}