package com.example.drevmassapp.domain.useCase.course

import com.example.drevmassapp.data.model.CourseDetailsDto
import com.example.drevmassapp.domain.repository.CourseRepository
import javax.inject.Inject

class GetCourseByIdUseCaseImpl @Inject constructor(
    private val repository: CourseRepository
): GetCourseByIdUseCase {
    override suspend fun getCourseById(token: String, courseId: Int): CourseDetailsDto {
        return repository.getCourseById(token, courseId)
    }
}