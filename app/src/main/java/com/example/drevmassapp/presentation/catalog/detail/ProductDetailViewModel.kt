package com.example.drevmassapp.presentation.catalog.detail

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.data.model.ProductDetailItemDto
import com.example.drevmassapp.domain.useCase.catalog.detail.AddToBasketUseCase
import com.example.drevmassapp.domain.useCase.catalog.detail.DecreaseItemUseCase
import com.example.drevmassapp.domain.useCase.catalog.detail.GetProductByIdUseCase
import com.example.drevmassapp.domain.useCase.catalog.detail.IncreaseItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addToBasketUseCase: dagger.Lazy<AddToBasketUseCase>,
    private val increaseItemUseCase: dagger.Lazy<IncreaseItemUseCase>,
    private val decreaseItemUseCase: dagger.Lazy<DecreaseItemUseCase>,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _detailState = MutableStateFlow<ProductDetailState>(ProductDetailState.Initial)
    val detailState = _detailState.asStateFlow()

    private val _basketButtonUiType = MutableLiveData(AddBasketButtonType.DEFAULT)
    val basketButtonUiType: LiveData<AddBasketButtonType> = _basketButtonUiType

    init {
        _basketButtonUiType.value = AddBasketButtonType.DEFAULT
        val savedType =
            sharedPreferences.getString("button_ui_type", AddBasketButtonType.DEFAULT.name)
        _basketButtonUiType.value = AddBasketButtonType.valueOf(savedType!!)
    }

    private val bearerToken = sharedPreferences.getString("accessToken", null)

    private val _buttonVisibility = MutableStateFlow(false)
    val buttonVisibility: StateFlow<Boolean> = _buttonVisibility

    // Function to change button visibility
    fun changeButtonVisibility(isVisible: Boolean) {
        _buttonVisibility.value = isVisible
    }
    fun getProductById(id: Int) {
        _detailState.value = ProductDetailState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getProductByIdUseCase.getProductById(bearerToken!!, id)
                _detailState.update { ProductDetailState.Success(result) }
                Log.d("ProductDetailViewModel", "getProductById: $result")
            } catch (e: Exception) {
                _detailState.update { ProductDetailState.Failure(e.message.toString()) }
                Log.d("ProductDetailViewModel", "getProductById error ${e.message.toString()}")

            }
        }
    }

    fun addToBasket(count: Int, productId: Int, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result =
                    addToBasketUseCase.get().addToBasket(bearerToken!!, count, productId, userId)
                Log.d("ProductDetailViewModel", "addToBasket: $result")
            } catch (e: Exception) {
                Log.d("ProductDetailViewModel", "addToBasket error ${e.message.toString()}")
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
    fun checkButtonState(item: ProductDetailItemDto){
        if(item.basketCount == 0){
            _basketButtonUiType.value = AddBasketButtonType.DEFAULT
        }else{
            _basketButtonUiType.value = AddBasketButtonType.ADD_BASKET
        }
    }

    fun loadButtonUiType(productId: Int){
        val savedType = sharedPreferences.getString("button_ui_type_$productId",AddBasketButtonType.DEFAULT.name)
        _basketButtonUiType.value = AddBasketButtonType.valueOf(savedType!!)
    }
    fun changeButtonUiType(productId: Int,type: AddBasketButtonType) {
        _basketButtonUiType.value = type
        sharedPreferences.edit().putString("button_ui_type_$productId", type.name).apply()
    }

}