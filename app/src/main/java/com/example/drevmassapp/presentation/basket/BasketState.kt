package com.example.drevmassapp.presentation.basket

import com.example.drevmassapp.data.model.BasketResponseDto

sealed interface BasketState{
    object Initial: BasketState
    object Loading: BasketState
    data class Success(val basket: BasketResponseDto): BasketState
    data class Failure(val message: String): BasketState
}