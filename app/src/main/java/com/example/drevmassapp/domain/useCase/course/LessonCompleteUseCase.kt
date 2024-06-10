package com.example.drevmassapp.domain.useCase.course

import com.example.drevmassapp.data.model.LessonCompleteDto

interface LessonCompleteUseCase {
    suspend fun lessonComplete(token: String,lessonId: Int,courseId: Int): LessonCompleteDto
}