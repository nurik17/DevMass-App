package com.example.drevmassapp.presentation.profileScreen

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.data.model.UserDto
import com.example.drevmassapp.domain.useCase.profile.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _user = MutableLiveData<UserDto>()
    val user: LiveData<UserDto> get() = _user

    private val bearerToken = sharedPreferences.getString("accessToken", null)

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            try {
                val userData = getUserUseCase.getUser(bearerToken!!)
                _user.value = userData
            } catch (e: Exception) {
                Log.d("ProfileScreenViewModel", e.message.toString())
            }
        }
    }
}