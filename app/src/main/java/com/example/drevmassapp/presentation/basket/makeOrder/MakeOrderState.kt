package com.example.drevmassapp.presentation.basket.makeOrder

import com.example.drevmassapp.data.model.ForgotPasswordDto

sealed interface MakeOrderState {
    object Initial : MakeOrderState
    object Loading : MakeOrderState
    data class Success(val order: ForgotPasswordDto) : MakeOrderState
    data class Failure(val message: String) : MakeOrderState
}
