package com.example.drevmassapp.domain.useCase.course

import com.example.drevmassapp.data.model.BookMarkDto

interface GetBookMarkUseCase {
    suspend fun getBookMarks(token: String): List<BookMarkDto>

}