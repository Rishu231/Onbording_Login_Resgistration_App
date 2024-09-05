package com.example.softwarelab.Models

data class ApiResponse<T>(
    val code: Int,
    val success: Boolean,
    val message: String,
    val response: T,
    val metadata: Metadata,
    val requestLocation: String
)
