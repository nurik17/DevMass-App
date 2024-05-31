package com.example.drevmassapp.domain.useCase.course

import com.example.drevmassapp.data.model.CourseDtotem

interface GetCourseUseCase {
    suspend fun getCourseList(token: String): List<CourseDtotem>
}