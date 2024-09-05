package com.example.softwarelab.Models

data class ResetPasswordResponse(
    val success: Boolean,
    val message: String,
    val is_verified: Boolean? = null
)