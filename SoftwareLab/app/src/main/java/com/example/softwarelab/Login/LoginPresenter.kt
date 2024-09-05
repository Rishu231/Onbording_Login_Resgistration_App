package com.example.softwarelab.Login

import com.example.softwarelab.ApiCall.ApiClient
import com.example.softwarelab.Login.view.ILoginPresenter
import com.example.softwarelab.Login.view.ILoginView
import com.example.softwarelab.Models.ForgotPasswordRequest
import com.example.softwarelab.Models.ForgotPasswordResponse
import com.example.softwarelab.Models.Login
import com.example.softwarelab.Models.ResetPasswordRequest
import com.example.softwarelab.Models.VerifyOtpRequest
import com.example.softwarelab.Models.VerifyOtpResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginPresenter (private var iView: ILoginView) : ILoginPresenter{


    override fun getLogin(userRequestLogin: Login) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.apiService.getLogin(userRequestLogin)
                if (response.isSuccessful && response.body()?.success == true) {
                    withContext(Dispatchers.Main) {
                        iView.showLoginResponse("Login Successful")
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        iView.showError(response.body()?.message ?: "Unknown error")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    iView.showError(e.message ?: "Error fetching data")
                }
            }
        }
    }

    override fun sendOtp(mobile: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = ForgotPasswordRequest(mobile)
                val response = ApiClient.apiService.sendMobile(request)
                withContext(Dispatchers.Main) {
                    if (response.success) {
                        iView.showForgotPasswordSuccess(response.message)
                    } else {
//                        iView.showError(response.message)
                        //remove code after completion
                        iView.showForgotPasswordSuccess(response.message)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    val fpr = ForgotPasswordResponse(success= true, message= "Success")
                    iView.showForgotPasswordSuccess(fpr.message)
//                    iView.showError(e.message ?: "Error occurred while requesting OTP")
                }
            }
        }
    }

    override fun submitOtp(otp: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = VerifyOtpRequest(otp)
                val response = ApiClient.apiService.verifyOtp(request)
                withContext(Dispatchers.Main) {
                    if (response.success) {
                        iView.submitOtpSuccess(response)
                    } else {
//                        iView.showError(response.message)
                        //remove code after completion
                        iView.submitOtpSuccess(response)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    iView.submitOtpSuccess(VerifyOtpResponse( success = false, message= "success", token= "1234"))
//                    iView.showError(e.message ?: "Error occurred while requesting OTP")
                }
            }
        }
    }

    override fun resetPassword(token: String, password: String, cpassword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = ResetPasswordRequest(token, password, cpassword)
                val response = ApiClient.apiService.resetPassword(request)
                withContext(Dispatchers.Main) {
                    if (response.success) {
                        iView.showPasswordResetSuccess(response.message)
                    } else {
//                        iView.showError(response.message)
                        //remove code after completion
                        iView.showPasswordResetSuccess(response.message)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
//                    iView.showPasswordResetSuccess(e.message?:"")
                    iView.showError(e.message ?: "Error occurred while requesting OTP")
                }
            }
        }
    }
}