package com.example.softwarelab.Models

data class ResetPasswordRequest(
    val token: String,
    val password: String,
    val cpassword: String
)
