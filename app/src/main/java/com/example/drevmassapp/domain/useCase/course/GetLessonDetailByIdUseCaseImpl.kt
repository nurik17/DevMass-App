package com.example.drevmassapp.domain.useCase.course

import com.example.drevmassapp.data.model.Lesson
import com.example.drevmassapp.domain.repository.CourseRepository
import javax.inject.Inject

class GetLessonDetailByIdUseCaseImpl @Inject constructor(
    private val repository: CourseRepository
) : GetLessonDetailByIdUseCase {
    override suspend fun getLessonsDetailById(token: String, courseId: Int, lessonId: Int): Lesson {
        return repository.getLessonsDetailById(token, courseId, lessonId)
    }
}