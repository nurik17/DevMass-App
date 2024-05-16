package com.example.drevmassapp.presentation.catalog

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.useCase.catalog.GetFamousProductUseCase
import com.example.drevmassapp.domain.useCase.catalog.GetProductsPriceDownUseCase
import com.example.drevmassapp.domain.useCase.catalog.GetProductsPriceUpUseCase
import com.example.drevmassapp.domain.useCase.catalog.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getFamousProductUseCase: GetFamousProductUseCase,
    private val getProductsPriceDownUseCase: GetProductsPriceDownUseCase,
    private val getProductsPriceUpUseCase: GetProductsPriceUpUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _catalogState = MutableStateFlow<CatalogState>(CatalogState.Loading)
    val catalogState = _catalogState.asStateFlow()

    private val _selectedOption = MutableLiveData<String>()
    val selectedOption: LiveData<String> = _selectedOption

    private val bearerToken = sharedPreferences.getString("accessToken", null)

    private fun loadSelectedOption() {
        val defaultOption = "По популярности"
        _selectedOption.value = sharedPreferences.getString(PREF_KEY, defaultOption)
    }

    fun updateSelectedOption(option: String) {
        _selectedOption.value = option
        with(sharedPreferences.edit()) {
            putString(PREF_KEY, option)
            apply()
        }
    }

    init {
        getProducts()
        loadSelectedOption()
    }

    fun getProducts() {
        _catalogState.value = CatalogState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getProductsUseCase.getProducts(bearerToken!!)
                _catalogState.value = CatalogState.Products(result)
                Log.d("CatalogViewModel", "getProducts: $result")
            } catch (e: Exception) {
                _catalogState.value = CatalogState.Failure(e.message.toString())
                Log.d("CatalogViewModel", "getProducts error ${e.message.toString()}")
            }
        }
    }

    fun getFamousProducts() {
        _catalogState.value = CatalogState.Loading
        viewModelScope.launch {
            try {
                val result = getFamousProductUseCase.getFamousProducts(bearerToken!!)
                _catalogState.value = CatalogState.FamousProducts(result)
                Log.d("CatalogViewModel", "getFamousProducts success")
            } catch (e: Exception) {
                _catalogState.value = CatalogState.Failure(e.message.toString())
                Log.d("CatalogViewModel", "getFamousProducts error ${e.message.toString()}")
            }
        }
    }

    fun getProductsPriceDown() {
        _catalogState.value = CatalogState.Loading
        viewModelScope.launch {
            try {
                val result = getProductsPriceDownUseCase.getProductsPriceDown(bearerToken!!)
                _catalogState.value = CatalogState.ProductsPriceDown(result)
                Log.d("CatalogViewModel", "getProductsPriceDown success")
            } catch (e: Exception) {
                _catalogState.value = CatalogState.Failure(e.message.toString())
                Log.d("CatalogViewModel", "getProductsPriceDown error ${e.message.toString()}")
            }
        }
    }

    fun getProductsPriceUp() {
        _catalogState.value = CatalogState.Loading
        viewModelScope.launch {
            try {
                val result = getProductsPriceUpUseCase.getProductsPriceUp(bearerToken!!)
                _catalogState.value = CatalogState.ProductsPriceUp(result)
                Log.d("CatalogViewModel", "getProductsPriceUp success")

            } catch (e: Exception) {
                _catalogState.update { CatalogState.Failure(e.message.toString()) }
                Log.d("CatalogViewModel", "getProductsPriceUp error ${e.message.toString()}")
            }
        }
    }

    companion object {
        private const val PREF_KEY = "selected_sort_option"
    }
}