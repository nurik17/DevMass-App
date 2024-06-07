package com.example.drevmassapp.domain.useCase.course

import com.example.drevmassapp.data.model.Lesson

interface GetLessonDetailByIdUseCase {
    suspend fun getLessonsDetailById(token: String,courseId: Int,lessonId: Int): Lesson
}