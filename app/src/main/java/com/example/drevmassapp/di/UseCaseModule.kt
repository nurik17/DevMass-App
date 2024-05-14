package com.example.drevmassapp.di

import com.example.drevmassapp.domain.useCases.registration.SignUpUseCaseImpl
import com.example.drevmassapp.domain.useCases.registration.SingUpUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
interface UseCaseModule {
    @Binds
    fun provideSignUpUseCase(impl: SignUpUseCaseImpl): SingUpUseCase
}