package com.example.drevmassapp.presentation.login

import com.example.drevmassapp.data.model.ForgotPasswordDto

sealed interface ForgotPasswordState{
    object Initial : ForgotPasswordState
    object Loading : ForgotPasswordState
    data class Failure(val message: String) : ForgotPasswordState
    data class Success(val token: ForgotPasswordDto) : ForgotPasswordState
}