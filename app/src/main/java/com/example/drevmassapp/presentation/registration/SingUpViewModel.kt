package com.example.drevmassapp.presentation.registration

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.useCase.registration.SingUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingUpViewModel @Inject constructor(
    private val signUpUseCase: SingUpUseCase,
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Initial)
    val signUpState = _signUpState.asStateFlow()


    fun signUp(deviceToken: String,email: String, name: String, password: String, phoneNumber: String){
        _signUpState.value = SignUpState.Loading
        viewModelScope.launch {
            try{
                val result = signUpUseCase.signUp(deviceToken, email, name, password, phoneNumber)
                _signUpState.value = SignUpState.Success(result)
            }catch (e: Exception){
                _signUpState.value = SignUpState.Failure(e.message.toString())
            }
        }
    }

    fun changeState(){
        _signUpState.value = SignUpState.Initial
    }
}