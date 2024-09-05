package com.example.softwarelab.ApiCall

import com.example.softwarelab.Models.ApiResponse
import com.example.softwarelab.Models.ForgotPasswordRequest
import com.example.softwarelab.Models.ForgotPasswordResponse
import com.example.softwarelab.Models.Login
import com.example.softwarelab.Models.LoginResponse
import com.example.softwarelab.Models.Register
import com.example.softwarelab.Models.RegistrationResponse
import com.example.softwarelab.Models.ResetPasswordRequest
import com.example.softwarelab.Models.ResetPasswordResponse
import com.example.softwarelab.Models.VerifyOtpRequest
import com.example.softwarelab.Models.VerifyOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @POST("user/login")
    suspend fun getLogin(@Body loginRequest: Login): Response<LoginResponse>

    @POST("user/forgot-password")
    suspend fun sendMobile(@Body forgotPasswordRequest: ForgotPasswordRequest): ForgotPasswordResponse

    @POST("user/verify-otp")
    suspend fun verifyOtp(@Body otp: VerifyOtpRequest): VerifyOtpResponse

    @POST("user/reset-password")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): ResetPasswordResponse

    @POST("user/register")
    suspend fun registrationData(@Body register: Register): RegistrationResponse

}
