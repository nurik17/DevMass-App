package com.example.drevmassapp.domain.useCase.course

import com.example.drevmassapp.data.model.Lesson

interface GetLessonListUseCase {
    suspend fun getLessonsById(token: String,courseId: Int): List<Lesson>
}