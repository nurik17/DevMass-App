package com.example.drevmassapp.domain.useCase.basket

import com.example.drevmassapp.data.model.BasketResponseDto
import com.example.drevmassapp.domain.repository.BasketRepository
import javax.inject.Inject

class GetBasketUseCaseImpl @Inject constructor(
    private val repository: BasketRepository
): GetBasketUseCase {
    override suspend fun getBasket(token: String, isUsing: String): BasketResponseDto {
        return repository.getBasket(token, isUsing)
    }
}