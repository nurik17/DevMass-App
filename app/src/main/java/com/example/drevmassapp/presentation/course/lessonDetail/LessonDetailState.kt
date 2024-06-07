package com.example.drevmassapp.presentation.course.lessonDetail

import com.example.drevmassapp.data.model.Lesson

sealed interface LessonDetailState {
    object Initial : LessonDetailState
    object Loading : LessonDetailState
    data class Success(val lesson: Lesson) : LessonDetailState
    data class Failure(val message: String) : LessonDetailState
}