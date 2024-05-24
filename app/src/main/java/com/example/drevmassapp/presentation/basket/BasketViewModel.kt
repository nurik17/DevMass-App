package com.example.drevmassapp.presentation.basket

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.data.model.Basket
import com.example.drevmassapp.domain.useCase.basket.DeleteAllBasketItemsUseCase
import com.example.drevmassapp.domain.useCase.basket.GetBasketUseCase
import com.example.drevmassapp.domain.useCase.catalog.detail.DecreaseItemUseCase
import com.example.drevmassapp.domain.useCase.catalog.detail.IncreaseItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val getBasketUseCase: GetBasketUseCase,
    private val deleteAllBasketItemsUseCase: dagger.Lazy<DeleteAllBasketItemsUseCase>,
    private val increaseItemUseCase: dagger.Lazy<IncreaseItemUseCase>,
    private val decreaseItemUseCase: dagger.Lazy<DecreaseItemUseCase>,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _basketState = MutableStateFlow<BasketState>(BasketState.Initial)
    val basketState = _basketState.asStateFlow()

    private val _isDeleteDialogVisible = MutableStateFlow(false)
    val isDeleteDialogVisible = _isDeleteDialogVisible.asStateFlow()

    private val _deleteIconVisibility = MutableStateFlow(false)
    val deleteIconVisibility = _deleteIconVisibility.asStateFlow()

    private val bearerToken = sharedPreferences.getString("accessToken", null)

    fun getBasket(isUsing: String) {
        _basketState.value = BasketState.Loading
        viewModelScope.launch {
            try {
                val result = getBasketUseCase.getBasket(token = bearerToken!!, isUsing = isUsing)
                _basketState.value = BasketState.Success(result)
            } catch (e: Exception) {
                _basketState.value = BasketState.Failure(e.message.toString())
                Log.d("getBasket", e.message.toString())
            }
        }
    }

    fun deleteAllBasketItems() {
        viewModelScope.launch {
            try {
                val result = deleteAllBasketItemsUseCase.get().deleteAllBasketItems(bearerToken!!)
                Log.d("ProductDetailViewModel", "addToBasket: $result")
                getBasket("")
            } catch (e: Exception) {
                Log.d("deleteAllBasketItems", e.message.toString())
            }
        }
    }

    fun increaseItem(count: Int, productId: Int, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result =
                    increaseItemUseCase.get().increaseBasketItem(bearerToken!!, count, productId, userId)
                Log.d("ProductDetailViewModel", "addToBasket: $result")
            } catch (e: Exception) {
                Log.d("ProductDetailViewModel", "addToBasket error ${e.message.toString()}")
            }
        }
    }

    fun decreaseItem(count: Int, productId: Int, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result =
                    decreaseItemUseCase.get().decreaseBasketItem(bearerToken!!, count, productId, userId)
                Log.d("ProductDetailViewModel", "addToBasket: $result")
            } catch (e: Exception) {
                Log.d("ProductDetailViewModel", "addToBasket error ${e.message.toString()}")
            }
        }
    }

    fun changeVisibilityDeleteDialog(value: Boolean) {
        viewModelScope.launch {
            _isDeleteDialogVisible.emit(value)
        }
    }

    fun checkDeleteIconVisibility(basketList: List<Basket>) {
        _deleteIconVisibility.value = basketList.isNotEmpty()
    }
}