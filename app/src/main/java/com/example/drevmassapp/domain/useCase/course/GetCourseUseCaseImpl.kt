package com.example.drevmassapp.domain.useCase.course

import com.example.drevmassapp.data.model.CourseDtotem
import com.example.drevmassapp.domain.repository.CourseRepository
import javax.inject.Inject

class GetCourseUseCaseImpl @Inject constructor(
    private val repository: CourseRepository
) : GetCourseUseCase {
    override suspend fun getCourseList(token: String): List<CourseDtotem> {
        return repository.getCourseList(token)
    }
}