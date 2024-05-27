package com.example.drevmassapp.presentation.profileScreen.promocode

import com.example.drevmassapp.data.model.Promocode

sealed interface PromocodeState {
    object Initial : PromocodeState
    object Loading : PromocodeState
    data class Failure(val message: String) : PromocodeState
    data class Success(val promocode: Promocode) : PromocodeState
}