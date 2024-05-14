package com.example.drevmassapp.presentation.registration

import com.example.drevmassapp.data.model.SignUpResponseDto

sealed interface SignUpState {

    object Initial: SignUpState
    object Loading: SignUpState
    data class Failure(val message : String): SignUpState
    data class Success(val token: SignUpResponseDto): SignUpState
}