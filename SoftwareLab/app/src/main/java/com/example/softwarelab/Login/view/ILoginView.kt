package com.example.softwarelab.Login.view

import com.example.softwarelab.Models.VerifyOtpResponse

interface ILoginView {
    fun showError(s: String)
    fun showLoginResponse(response: String)
    fun showForgotPasswordSuccess(message: String)
    fun submitOtpSuccess(message: VerifyOtpResponse)
    fun showPasswordResetSuccess(message: String)
}
