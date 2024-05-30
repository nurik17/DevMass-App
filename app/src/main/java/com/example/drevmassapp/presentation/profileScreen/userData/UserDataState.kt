package com.example.drevmassapp.presentation.profileScreen.userData

import com.example.drevmassapp.domain.entity.UpdateUserBody


sealed interface UserDataState {
    object Initial : UserDataState
    object Loading : UserDataState
    data class Failure(val message: String) : UserDataState
    data class Success(val userData: UpdateUserBody) : UserDataState
}