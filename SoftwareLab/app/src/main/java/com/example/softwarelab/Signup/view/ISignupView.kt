package com.example.softwarelab.Signup.view

import com.example.softwarelab.Models.RegistrationResponse

interface ISignupView {
     fun showError(message: String)
     fun saveToken(data: RegistrationResponse)

}
