package com.example.softwarelab.Models

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String,
    val user: User,
    val account_preference: AccountPreference,
    val notification_settings: NotificationSettings,
    val is_verified: Boolean
)

data class User(
    val id: String,
    val avatar: String,
    val full_name: String,
    val email: String,
    val device_token: String,
    val type: String,
    val social_id: String
)

data class AccountPreference(
    val locale: String,
    val time_zone: String,
    val currency: String
)

data class NotificationSettings(
    val notificationSettings: String,
    val minBidThreshold: String
)
