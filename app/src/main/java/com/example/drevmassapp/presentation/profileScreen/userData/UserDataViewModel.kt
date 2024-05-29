package com.example.drevmassapp.presentation.profileScreen.userData

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.useCase.profile.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _userPhone = MutableLiveData<String>()
    val userPhone: LiveData<String> get() = _userPhone

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val bearerToken = sharedPreferences.getString("accessToken", null)

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val userData = getUserUseCase.getUser(bearerToken!!)
                _userName.value = userData.name ?: ""
                _userPhone.value = userData.phoneNumber ?: ""
                _userEmail.value = userData.email ?: ""
            } catch (e: Exception) {
                Log.d("UserDataViewModel", e.message.toString())
            }
            _isLoading.value = false
        }
    }

    fun setName(name: String) {
        _userName.value = name
    }

    fun setPhone(phone: String) {
        _userPhone.value = phone
    }

    fun setEmail(email: String) {
        _userEmail.value = email
    }
}