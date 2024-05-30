package com.example.drevmassapp.presentation.profileScreen

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.data.model.UserDto
import com.example.drevmassapp.domain.useCase.profile.GetUserUseCase
import com.example.drevmassapp.domain.useCase.profile.ResetPasswordUseCase
import com.example.drevmassapp.domain.useCase.profile.SendSupportTextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val sendSupportTextUseCase: dagger.Lazy<SendSupportTextUseCase>,
    private val resetPasswordUseCase: dagger.Lazy<ResetPasswordUseCase>,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _user = MutableLiveData<UserDto>()
    val user: LiveData<UserDto> get() = _user

    private val _supportText = MutableLiveData<ForgotPasswordDto>()
    val supportText: LiveData<ForgotPasswordDto> get() = _supportText

    private val _resetPassword = MutableLiveData<ForgotPasswordDto>()
    val resetPassword: LiveData<ForgotPasswordDto> get() = _resetPassword

    private val _isDeleteDialogVisible = MutableStateFlow(false)
    val isDeleteDialogVisible = _isDeleteDialogVisible.asStateFlow()


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

    fun sendSupportText(message: String) {
        viewModelScope.launch {
            try {
                val result = sendSupportTextUseCase.get().sendSupportText(bearerToken!!, message)
                _supportText.value = result
            } catch (e: Exception) {
                Log.d("ProfileViewModel", "sendSupportText:${e.message.toString()}")
            }
        }
    }

    fun resetPassword(currentPassword: String, newPassword: String) {
        viewModelScope.launch {
            try {
                val result = resetPasswordUseCase.get()
                    .resetPassword(bearerToken!!, currentPassword, newPassword)
                _resetPassword.value = result
            } catch (e: Exception) {
                Log.d("ProfileViewModel", "resetPassword:${e.message.toString()}")
            }
        }
    }

    fun changeVisibilityDeleteDialog(value: Boolean) {
        viewModelScope.launch {
            _isDeleteDialogVisible.emit(value)
        }
    }
}