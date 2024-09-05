package com.example.softwarelab.Signup

import com.example.softwarelab.ApiCall.ApiClient
import com.example.softwarelab.Models.Register
import com.example.softwarelab.Signup.view.ISignupPresenter
import com.example.softwarelab.Signup.view.ISignupView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupPresenter (private var iView: ISignupView) : ISignupPresenter {
    override fun Registeration(register: Register) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.apiService.registrationData(register)
                withContext(Dispatchers.Main) {
                    if (response.success) {
                        iView.saveToken(response)
                    } else {
                        iView.showError(response.message)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    iView.showError(e.message ?: "Error occurred while requesting OTP")
                }
            }
        }
    }

}