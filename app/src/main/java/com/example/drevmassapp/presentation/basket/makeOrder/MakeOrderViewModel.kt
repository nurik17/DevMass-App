package com.example.drevmassapp.presentation.basket.makeOrder

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.entity.OrderedProduct
import com.example.drevmassapp.domain.useCase.basket.MakeOrderUseCase
import com.example.drevmassapp.domain.useCase.catalog.detail.IncreaseItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MakeOrderViewModel @Inject constructor(
    private val makeOrderUseCase: dagger.Lazy<MakeOrderUseCase>,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _makeOrderState = MutableStateFlow<MakeOrderState>(MakeOrderState.Initial)
    val makeOrderState = _makeOrderState.asStateFlow()

    private val bearerToken = sharedPreferences.getString("accessToken", null)

    fun makeOrder(
        bonus: Int,
        crmLink: String,
        email: String,
        phoneNumber: String,
        products: List<OrderedProduct>,
        totalPrice: Int,
        userName: String
    ) {
        _makeOrderState.update { MakeOrderState.Loading }
        viewModelScope.launch {
            try {
                val result = makeOrderUseCase.get().makeOrder(
                    bearerToken!!,
                    bonus,
                    crmLink,
                    email,
                    phoneNumber,
                    products,
                    totalPrice,
                    userName
                )
                _makeOrderState.update { MakeOrderState.Success(result) }
            } catch (e: Exception) {
                _makeOrderState.update { MakeOrderState.Failure(e.message.toString()) }
            }
        }
    }
}