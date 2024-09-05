package com.example.softwarelab.Models

data class Login(
    val email: String,
    val password: String,
    val role: String,
    val device_token: String,
    val type: String,
    val social_id: String
)
