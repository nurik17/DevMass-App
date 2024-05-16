package com.example.drevmassapp.di

import com.example.drevmassapp.domain.useCase.catalog.GetFamousProductUseCase
import com.example.drevmassapp.domain.useCase.catalog.GetFamousProductUseCaseImpl
import com.example.drevmassapp.domain.useCase.catalog.GetProductsPriceDownUseCase
import com.example.drevmassapp.domain.useCase.catalog.GetProductsPriceDownUseCaseImpl
import com.example.drevmassapp.domain.useCase.catalog.GetProductsPriceUpUseCase
import com.example.drevmassapp.domain.useCase.catalog.GetProductsPriceUpUseCaseImpl
import com.example.drevmassapp.domain.useCase.catalog.GetProductsUseCase
import com.example.drevmassapp.domain.useCase.catalog.GetProductsUseCaseImpl
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

    @Binds
    fun provideGetProductsUseCase(impl: GetProductsUseCaseImpl): GetProductsUseCase

    @Binds
    fun provideGetFamousProductUseCase(impl: GetFamousProductUseCaseImpl): GetFamousProductUseCase
    @Binds
    fun provideGetProductsPriceDownUseCase(impl: GetProductsPriceDownUseCaseImpl): GetProductsPriceDownUseCase
    @Binds
    fun provideGetProductsPriceUpUseCase(impl: GetProductsPriceUpUseCaseImpl): GetProductsPriceUpUseCase

}