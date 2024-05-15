package com.example.drevmassapp.di

import com.example.drevmassapp.domain.useCase.registration.ForgotPasswordUseCase
import com.example.drevmassapp.domain.useCase.registration.ForgotPasswordUseCaseImpl
import com.example.drevmassapp.domain.useCase.registration.LoginUseCase
import com.example.drevmassapp.domain.useCase.registration.LoginUseCaseImpl
import com.example.drevmassapp.domain.useCase.registration.SignUpUseCaseImpl
import com.example.drevmassapp.domain.useCase.registration.SingUpUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
interface UseCaseModule {
    @Binds
    fun provideSignUpUseCase(impl: SignUpUseCaseImpl): SingUpUseCase

    @Binds
    fun provideLoginUseCase(impl: LoginUseCaseImpl): LoginUseCase

    @Binds
    fun provideForgotPasswordUseCase(impl: ForgotPasswordUseCaseImpl): ForgotPasswordUseCase


}