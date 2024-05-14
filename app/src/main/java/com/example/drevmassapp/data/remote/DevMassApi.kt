package com.example.drevmassapp.data.remote

import com.example.drevmassapp.data.model.SignUpResponseDto
import com.example.drevmassapp.domain.entity.SignUpBody
import retrofit2.http.Body
import retrofit2.http.POST

interface DevMassApi {

    @POST("api/signup")
    suspend fun singUp(
        @Body body: SignUpBody
    ): SignUpResponseDto

}