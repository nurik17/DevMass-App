package com.example.drevmassapp.domain.useCase.course

import com.example.drevmassapp.data.model.CourseStartDto
import com.example.drevmassapp.domain.repository.CourseRepository
import javax.inject.Inject

class StartCourseUseCaseImpl @Inject constructor(
    private val repository: CourseRepository
): StartCourseUseCase {
    override suspend fun startCourse(token: String, courseId: Int): CourseStartDto {
        return repository.startCourse(token,courseId)
    }
}