package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.data.model.UserDto

interface ProfileRepository{
    suspend fun getUser(token: String): UserDto
}