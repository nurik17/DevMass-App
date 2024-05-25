package com.example.drevmassapp.data.remote

import com.example.drevmassapp.data.model.BasketResponseDto
import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.data.model.ProductDetailDto
import com.example.drevmassapp.data.model.ProductX
import com.example.drevmassapp.data.model.SignInResponseDto
import com.example.drevmassapp.data.model.SignUpResponseDto
import com.example.drevmassapp.domain.entity.AddToBasketBody
import com.example.drevmassapp.domain.entity.MakeOrderBody
import com.example.drevmassapp.domain.entity.SignInBody
import com.example.drevmassapp.domain.entity.SignUpBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DevMassApi {
    @POST("signup")
    suspend fun singUp(
        @Body body: SignUpBody
    ): SignUpResponseDto

    @POST("login")
    suspend fun login(
        @Body body: SignInBody
    ): SignInResponseDto

    @FormUrlEncoded
    @POST("forgot_password")
    suspend fun forgotPassword(
        @Field("email") email: String,
    ): ForgotPasswordDto

    @GET("products")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ): ProductX

    @GET("products/famous")
    suspend fun getFamousProducts(
        @Header("Authorization") token: String
    ): ProductX

    @GET("products/pricedown")
    suspend fun getProductsPriceDown(
        @Header("Authorization") token: String
    ): ProductX

    @GET("products/priceup")
    suspend fun getProductsPriceUp(
        @Header("Authorization") token: String
    ): ProductX

    @GET("products/{product_id}")
    suspend fun getProductById(
        @Header("Authorization") token: String,
        @Path("product_id") id: Int
    ): ProductDetailDto

    @POST("basket")
    suspend fun addToBasket(
        @Header("Authorization") token: String,
        @Body body: AddToBasketBody
    ): ForgotPasswordDto

    @POST("increase")
    suspend fun increaseBasketItem(
        @Header("Authorization") token: String,
        @Body body: AddToBasketBody
    ): ForgotPasswordDto

    @POST("decrease")
    suspend fun decreaseBasketItem(
        @Header("Authorization") token: String,
        @Body body: AddToBasketBody
    ): ForgotPasswordDto

    @GET("basket")
    suspend fun getBasket(
        @Header("Authorization") token: String,
        @Query("is_using") isUsing: String
    ): BasketResponseDto

    @DELETE("basket")
    suspend fun deleteAllBasketItems(
        @Header("Authorization") token: String,
    ): ForgotPasswordDto

    @POST("order")
    suspend fun makeOrder(
        @Header("Authorization") token: String,
        @Body body: MakeOrderBody,
    ): ForgotPasswordDto
}
