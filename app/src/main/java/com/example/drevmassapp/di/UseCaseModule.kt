package com.example.drevmassapp.di

import com.example.drevmassapp.domain.repository.BasketRepository
import com.example.drevmassapp.domain.useCase.basket.DeleteAllBasketItemsUseCase
import com.example.drevmassapp.domain.useCase.basket.DeleteAllBasketItemsUseCaseImpl
import com.example.drevmassapp.domain.useCase.basket.GetBasketUseCase
import com.example.drevmassapp.domain.useCase.basket.GetBasketUseCaseImpl
import com.example.drevmassapp.domain.useCase.basket.MakeOrderUseCase
import com.example.drevmassapp.domain.useCase.basket.MakeOrderUseCaseImpl
import com.example.drevmassapp.domain.useCase.catalog.GetFamousProductUseCase
import com.example.drevmassapp.domain.useCase.catalog.GetFamousProductUseCaseImpl
import com.example.drevmassapp.domain.useCase.catalog.detail.GetProductByIdUseCase
import com.example.drevmassapp.domain.useCase.catalog.detail.GetProductByIdUseCaseImpl
import com.example.drevmassapp.domain.useCase.catalog.GetProductsPriceDownUseCase
import com.example.drevmassapp.domain.useCase.catalog.GetProductsPriceDownUseCaseImpl
import com.example.drevmassapp.domain.useCase.catalog.GetProductsPriceUpUseCase
import com.example.drevmassapp.domain.useCase.catalog.GetProductsPriceUpUseCaseImpl
import com.example.drevmassapp.domain.useCase.catalog.GetProductsUseCase
import com.example.drevmassapp.domain.useCase.catalog.GetProductsUseCaseImpl
import com.example.drevmassapp.domain.useCase.catalog.detail.AddToBasketUseCase
import com.example.drevmassapp.domain.useCase.catalog.detail.AddToBasketUseCaseImpl
import com.example.drevmassapp.domain.useCase.catalog.detail.DecreaseItemUseCase
import com.example.drevmassapp.domain.useCase.catalog.detail.DecreaseItemUseCaseImpl
import com.example.drevmassapp.domain.useCase.catalog.detail.IncreaseItemUseCase
import com.example.drevmassapp.domain.useCase.catalog.detail.IncreaseItemUseCaseImpl
import com.example.drevmassapp.domain.useCase.profile.GetBonusInfoUseCase
import com.example.drevmassapp.domain.useCase.profile.GetBonusInfoUseCaseImpl
import com.example.drevmassapp.domain.useCase.profile.GetBonusUseCase
import com.example.drevmassapp.domain.useCase.profile.GetBonusUseCaseImpl
import com.example.drevmassapp.domain.useCase.profile.GetPromocodeUseCase
import com.example.drevmassapp.domain.useCase.profile.GetPromocodeUseCaseImpl
import com.example.drevmassapp.domain.useCase.profile.GetUserUseCase
import com.example.drevmassapp.domain.useCase.profile.GetUserUseCaseImpl
import com.example.drevmassapp.domain.useCase.registration.ForgotPasswordUseCase
import com.example.drevmassapp.domain.useCase.registration.ForgotPasswordUseCaseImpl
import com.example.drevmassapp.domain.useCase.registration.LoginUseCase
import com.example.drevmassapp.domain.useCase.registration.LoginUseCaseImpl
import com.example.drevmassapp.domain.useCase.registration.SignUpUseCaseImpl
import com.example.drevmassapp.domain.useCase.registration.SingUpUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
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

    @Binds
    fun provideGetProductByIdUseCase(impl: GetProductByIdUseCaseImpl): GetProductByIdUseCase

    @Binds
    fun provideAddToBasketUseCase(impl: AddToBasketUseCaseImpl): AddToBasketUseCase

    @Binds
    fun provideIncreaseItemUseCase(impl: IncreaseItemUseCaseImpl): IncreaseItemUseCase

    @Binds
    fun provideDecreaseItemUseCase(impl: DecreaseItemUseCaseImpl): DecreaseItemUseCase

   /* @Provides
    fun provideGetBasketUseCase(repo: BasketRepository): GetBasketUseCase =
        GetBasketUseCaseImpl(repo)*/

    @Binds
    fun provideGetBasketUseCase(impl: GetBasketUseCaseImpl): GetBasketUseCase

    @Binds
    fun provideDeleteAllBasketItemsUseCase(impl: DeleteAllBasketItemsUseCaseImpl): DeleteAllBasketItemsUseCase

    @Binds
    fun provideMakeOrderUseCase(impl: MakeOrderUseCaseImpl): MakeOrderUseCase

    @Binds
    fun provideGetUserUseCase(impl: GetUserUseCaseImpl): GetUserUseCase

    @Binds
    fun provideGetBonusUseCase(impl: GetBonusUseCaseImpl): GetBonusUseCase

    @Binds
    fun provideGetBonusInfoUseCase(impl: GetBonusInfoUseCaseImpl): GetBonusInfoUseCase

    @Binds
    fun provideGetPromocodeUseCase(impl: GetPromocodeUseCaseImpl): GetPromocodeUseCase
}