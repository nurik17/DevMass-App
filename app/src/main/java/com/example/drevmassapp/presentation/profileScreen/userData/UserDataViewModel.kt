package com.example.drevmassapp.presentation.profileScreen.userData

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.data.model.UserDto
import com.example.drevmassapp.domain.useCase.profile.DeleteUserAccountUseCase
import com.example.drevmassapp.domain.useCase.profile.GetUserUseCase
import com.example.drevmassapp.domain.useCase.profile.SendSupportTextUseCase
import com.example.drevmassapp.domain.useCase.profile.UpdateUserDataUseCase
import com.example.drevmassapp.presentation.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserDataUseCase: dagger.Lazy<UpdateUserDataUseCase>,
    private val deleteUserAccountUseCase: dagger.Lazy<DeleteUserAccountUseCase>,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _userPhone = MutableLiveData<String>()
    val userPhone: LiveData<String> get() = _userPhone

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    private val _userId = MutableLiveData<UserDto>()
    val userId: LiveData<UserDto> get() = _userId

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _updateUserDataState = MutableStateFlow<UserDataState>(UserDataState.Initial)
    val updateUserDataState = _updateUserDataState.asStateFlow()

    private val _deleteUser = MutableLiveData<ForgotPasswordDto>()
    val deleteUser: LiveData<ForgotPasswordDto> get() = _deleteUser

    private val _isDeleteDialogVisible = MutableStateFlow(false)
    val isDeleteDialogVisible = _isDeleteDialogVisible.asStateFlow()

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

    fun updateUserData(
        activity: Int,
        birth: String,
        email: String,
        gender: Int,
        height: Int,
        id: Int,
        name: String,
        phoneNumber: String,
        weight: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _updateUserDataState.update { UserDataState.Loading }
            try {
                val result = updateUserDataUseCase.get().updateUserData(
                    bearerToken!!,
                    activity,
                    birth,
                    email,
                    gender,
                    height,
                    id,
                    name,
                    phoneNumber,
                    weight
                )
                _updateUserDataState.update { UserDataState.Success(result) }
            } catch (e: Exception) {
                _updateUserDataState.update { UserDataState.Failure(e.message.toString()) }
                Log.d("UserDataViewModel", "updateUserData:${e.message.toString()}")
            }
        }
    }

    fun deleteUser() {
        viewModelScope.launch() {
            try {
                val result = deleteUserAccountUseCase.get().deleteUserAccount(bearerToken!!)
                _deleteUser.value = result
            }catch (e: Exception){
                Log.d("UserDataViewModel", "deleteUser: ${e.message.toString()}")
            }
        }
    }

    fun changeVisibilityDeleteDialog(value: Boolean) {
        viewModelScope.launch {
            _isDeleteDialogVisible.emit(value)
        }
    }

    fun changeState() {
        _updateUserDataState.value = UserDataState.Initial
    }
}