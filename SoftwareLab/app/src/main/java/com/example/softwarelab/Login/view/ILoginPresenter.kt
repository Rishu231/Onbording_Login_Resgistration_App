package com.example.softwarelab.Login.view

import com.example.softwarelab.Models.Login

interface ILoginPresenter {
    fun getLogin(userRequestLogin: Login)
    fun sendOtp(etPhone: String)
    fun submitOtp(otp: String)
    fun resetPassword(token: String, password: String, cpassword: String)
}
