package com.example.drevmassapp.domain.useCase.course

import com.example.drevmassapp.data.model.BookMarkDto
import com.example.drevmassapp.domain.repository.CourseRepository
import javax.inject.Inject

class GetBookMarkUseCaseImpl @Inject constructor(
    private val repository: CourseRepository
): GetBookMarkUseCase {
    override suspend fun getBookMarks(token: String): List<BookMarkDto> {
        return repository.getBookMarks(token)
    }
}