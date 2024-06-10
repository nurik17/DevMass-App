package com.example.drevmassapp.domain.useCase.course

import com.example.drevmassapp.data.model.CourseStartDto

interface StartCourseUseCase {
    suspend fun startCourse(token: String,courseId: Int): CourseStartDto
}