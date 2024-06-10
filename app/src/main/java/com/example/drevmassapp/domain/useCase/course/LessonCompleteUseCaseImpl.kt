package com.example.drevmassapp.domain.useCase.course

import com.example.drevmassapp.data.model.LessonCompleteDto
import com.example.drevmassapp.domain.repository.CourseRepository
import javax.inject.Inject

class LessonCompleteUseCaseImpl @Inject constructor(
    private val repository: CourseRepository
): LessonCompleteUseCase {
    override suspend fun lessonComplete(
        token: String,
        lessonId: Int,
        courseId: Int
    ): LessonCompleteDto {
        return repository.lessonComplete(token,lessonId, courseId)
    }
}