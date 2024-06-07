package com.example.drevmassapp.domain.useCase.course

import com.example.drevmassapp.data.model.Lesson
import com.example.drevmassapp.domain.repository.CourseRepository
import javax.inject.Inject

class GetLessonListUseCaseImpl @Inject constructor(
    private val repository: CourseRepository
) : GetLessonListUseCase {
    override suspend fun getLessonsById(token: String, courseId: Int): List<Lesson> {
        return repository.getLessonsById(token, courseId)
    }
}