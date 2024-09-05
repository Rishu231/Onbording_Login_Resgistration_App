package com.example.softwarelab.Models

data class RegistrationResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null
)
