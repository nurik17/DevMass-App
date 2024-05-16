package com.example.drevmassapp.presentation.login

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.useCase.registration.ForgotPasswordUseCase
import com.example.drevmassapp.domain.useCase.registration.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
    val loginState = _loginState.asStateFlow()

    private val _forgotPasswordState = MutableStateFlow<ForgotPasswordState>(ForgotPasswordState.Initial)
    val forgotPasswordState = _forgotPasswordState.asStateFlow()

    fun login(deviceToken: String, email: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch(Dispatchers.IO){
            try {
                val result = loginUseCase.login(deviceToken, email, password)
                _loginState.value = LoginState.Success(result)
                saveToken(result.accessToken,result.refreshToken)
                Log.d("LoginViewModel", "login: ${result.accessToken}${result.refreshToken}")
            } catch (e: Exception) {
                _loginState.value = LoginState.Failure(e.message.toString())
            }
        }
    }

    fun forgotPassword(email: String){
        _forgotPasswordState.value = ForgotPasswordState.Loading
        viewModelScope.launch(Dispatchers.IO){
            try {
                val result = forgotPasswordUseCase.forgotPassword(email)
                _forgotPasswordState.value = ForgotPasswordState.Success(result)
            }catch (e: Exception){
                _forgotPasswordState.value = ForgotPasswordState.Failure(e.message.toString())
                Log.d("forgotPassword", e.message.toString())
            }
        }
    }

    fun changeState(){
        _loginState.value = LoginState.Initial
    }
    fun changeStateForgotPassword(){
        _forgotPasswordState.value = ForgotPasswordState.Initial
    }

    @SuppressLint("CommitPrefEdits")
    private fun saveToken(accessToken: String, refreshToken: String){
        val editor = sharedPreferences.edit()
        editor.putString("accessToken", accessToken)
        editor.putString("refreshToken",refreshToken)
        editor.apply()
    }
}