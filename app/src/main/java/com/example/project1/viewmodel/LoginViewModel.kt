package com.example.project1.viewmodel

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.project1.R
import java.util.concurrent.Executor


class LoginViewModel: ViewModel() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var authenticationCallback: BiometricAuthenticationCallback
    private lateinit var biometricLauncher: ActivityResultLauncher<Intent>


    interface BiometricAuthenticationCallback {
        fun onAuthenticationError(errorCode: Int, errString: CharSequence)
        fun onAuthenticationSucceeded()
        fun onAuthenticationFailed()
    }

    fun initBiometricLauncher(launcher: ActivityResultLauncher<Intent>) {
        biometricLauncher = launcher
    }

    fun initBiometricPrompt(host: Fragment, authCallback: BiometricAuthenticationCallback) {
        executor = ContextCompat.getMainExecutor(host.requireContext())
        authenticationCallback = authCallback
        biometricPrompt = BiometricPrompt(host, executor, createAuthenticationCallback())
        promptInfo = createPromptInfo(host.requireContext())
    }

    private fun createPromptInfo(context: Context): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle(context.getString(R.string.autenticaci_n_con_biometr_a))
            .setSubtitle(context.getString(R.string.ingrese_su_huella_digital))
            .setNegativeButtonText(context.getString(R.string.cancelar))
            .build()
    }

    private fun createAuthenticationCallback(): BiometricPrompt.AuthenticationCallback {
        return object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                authenticationCallback.onAuthenticationError(errorCode, errString)
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                authenticationCallback.onAuthenticationSucceeded()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                authenticationCallback.onAuthenticationFailed()

            }
        }
    }



    private fun checkDeviceHasBiometric(host: Fragment): Boolean {
        val biometricManager = BiometricManager.from(host.requireContext())
        var available = false
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                available = true

            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.e("MY_APP_TAG", "Dispositivo sin sensor biométrico.")

            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.e("MY_APP_TAG", "Características biométricas no disponibles")

            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {

                biometricLauncher.launch(Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK
                    )
                })
            }
            else -> {
                Log.e("MY_APP_TAG", "error")
            }

        }
        return available
    }


    fun handleFingerClick(host: Fragment) {
        if (checkDeviceHasBiometric(host)) {
            biometricPrompt.authenticate(promptInfo)
        }
    }
}

