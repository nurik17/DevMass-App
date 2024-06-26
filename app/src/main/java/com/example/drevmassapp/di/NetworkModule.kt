package com.example.drevmassapp.di

import com.example.drevmassapp.data.remote.DrevMassApi
import com.example.drevmassapp.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @DevMassAppUrl
    fun provideDevMassAppUrl(): String{
        return Constant.BASE_URL
    }

    @BasicOkHttpClient
    @Provides
    fun provideBasicOkHttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @DevMassAppUrl
    @Provides
    @Singleton
    fun getDevMassRetrofit(
        @DevMassAppUrl url: String,
        @BasicOkHttpClient okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getDevMassApi(@DevMassAppUrl retrofit: Retrofit): DrevMassApi {
        return retrofit.create(
            DrevMassApi::class.java
        )
    }

}

@Qualifier
annotation class DevMassAppUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BasicOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthenticatedOkHttpClient