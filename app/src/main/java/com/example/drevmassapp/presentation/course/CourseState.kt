package com.example.drevmassapp.presentation.course

import com.example.drevmassapp.data.model.CourseDtotem

sealed interface CourseState {
    object Initial : CourseState
    object Loading : CourseState
    data class Success(val course: List<CourseDtotem>) : CourseState
    data class Failure(val message: String) : CourseState
}