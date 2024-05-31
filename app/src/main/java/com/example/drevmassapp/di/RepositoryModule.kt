package com.example.drevmassapp.di

import com.example.drevmassapp.data.local.BasketRepositoryImpl
import com.example.drevmassapp.data.local.CatalogRepositoryImpl
import com.example.drevmassapp.data.local.CourseRepositoryImpl
import com.example.drevmassapp.data.local.ProfileRepositoryImpl
import com.example.drevmassapp.data.local.RegistrationRepositoryImpl
import com.example.drevmassapp.domain.repository.BasketRepository
import com.example.drevmassapp.domain.repository.CatalogRepository
import com.example.drevmassapp.domain.repository.CourseRepository
import com.example.drevmassapp.domain.repository.ProfileRepository
import com.example.drevmassapp.domain.repository.RegistrationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun provideRegistrationRepository(impl: RegistrationRepositoryImpl): RegistrationRepository

    @Binds
    fun provideCatalogRepository(impl: CatalogRepositoryImpl): CatalogRepository

    @Binds
    fun provideBasketRepository(impl: BasketRepositoryImpl): BasketRepository

    @Binds
    fun provideProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    fun provideCourseRepository(impl: CourseRepositoryImpl): CourseRepository
}