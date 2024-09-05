package com.example.softwarelab.Models

data class Reset_password_request(
    val token: String,
    val password: String,
    val cpassword: String
)
