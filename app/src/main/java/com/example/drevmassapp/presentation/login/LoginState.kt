package com.example.drevmassapp.presentation.login

import com.example.drevmassapp.data.model.SignInResponseDto

sealed interface LoginState {
    object Initial : LoginState
    object Loading : LoginState
    data class Failure(val message: String) : LoginState
    data class Success(val token: SignInResponseDto) : LoginState
}