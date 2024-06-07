package com.example.drevmassapp.domain.useCase.course

import com.example.drevmassapp.data.model.CourseDetailsDto

interface GetCourseByIdUseCase {
    suspend fun getCourseById(token: String, courseId: Int): CourseDetailsDto
}