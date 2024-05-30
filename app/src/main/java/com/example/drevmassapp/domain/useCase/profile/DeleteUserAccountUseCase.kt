package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.data.model.ForgotPasswordDto

interface DeleteUserAccountUseCase {
    suspend fun deleteUserAccount(token: String): ForgotPasswordDto

}