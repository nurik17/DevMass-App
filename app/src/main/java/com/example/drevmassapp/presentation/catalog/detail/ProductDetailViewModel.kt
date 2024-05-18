package com.example.drevmassapp.presentation.catalog.detail

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.useCase.catalog.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _detailState = MutableStateFlow<ProductDetailState>(ProductDetailState.Initial)
    val detailState = _detailState.asStateFlow()

    private val bearerToken = sharedPreferences.getString("accessToken", null)


    fun getProductById(id: Int) {
        _detailState.value = ProductDetailState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getProductByIdUseCase.getProductById(bearerToken!!, id)
                _detailState.value = ProductDetailState.Success(result)
                Log.d("ProductDetailViewModel", "getProductById: $result")
            } catch (e: Exception) {
                _detailState.value = ProductDetailState.Failure(e.message.toString())
                Log.d("ProductDetailViewModel", "getProductById error ${e.message.toString()}")

            }
        }
    }
}