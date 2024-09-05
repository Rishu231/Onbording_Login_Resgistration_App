package com.example.softwarelab.Models

data class VerifyOtpResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null
)
