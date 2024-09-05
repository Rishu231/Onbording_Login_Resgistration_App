package com.example.softwarelab.Models

data class BusinessHours(
    val mon: List<String>,
    val tue: List<String>,
    val wed: List<String>,
    val thu: List<String>,
    val fri: List<String>,
    val sat: List<String>,
    val sun: List<String>
)

data class Register(
    val full_name: String,
    val email: String,
    val phone: String,
    val password: String,
    val role: String,
    val businessName: String,
    val informalName: String,
    val address: String,
    val city: String,
    val state: String,
    val zipCode: Int,
    val registration_proof: String,
    val business_hours: BusinessHours,
    val device_token: String,
    val type: String,
    val social_id: String
)

