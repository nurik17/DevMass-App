package com.example.drevmassapp.domain.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResetPasswordBody(
    @SerializedName("current_password") val currentPassword: String,
    @SerializedName("new_password") val newPassword: String
)