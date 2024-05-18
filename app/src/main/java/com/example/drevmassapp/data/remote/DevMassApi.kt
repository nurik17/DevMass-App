package com.example.drevmassapp.data.remote

import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.data.model.ProductDetailDto
import com.example.drevmassapp.data.model.ProductX
import com.example.drevmassapp.data.model.SignInResponseDto
import com.example.drevmassapp.data.model.SignUpResponseDto
import com.example.drevmassapp.domain.entity.SignInBody
import com.example.drevmassapp.domain.entity.SignUpBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface DevMassApi {
    @POST("api/signup")
    suspend fun singUp(
        @Body body: SignUpBody
    ): SignUpResponseDto

    @POST("api/login")
    suspend fun login(
        @Body body: SignInBody
    ): SignInResponseDto

    @FormUrlEncoded
    @POST("api/forgot_password")
    suspend fun forgotPassword(
        @Field("email") email: String,
    ): ForgotPasswordDto

    @GET("api/products")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ): ProductX

    @GET("api/products/famous")
    suspend fun getFamousProducts(
        @Header("Authorization") token: String
    ):ProductX

    @GET("api/products/pricedown")
    suspend fun getProductsPriceDown(
        @Header("Authorization") token: String
    ):ProductX

    @GET("api/products/priceup")
    suspend fun getProductsPriceUp(
        @Header("Authorization") token: String
    ):ProductX

    @GET("api/products/{product_id}")
    suspend fun getProductById(
        @Header("Authorization") token: String,
        @Path("product_id") id : Int
    ):ProductDetailDto
}