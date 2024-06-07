package com.example.drevmassapp.presentation.course.detail

import com.example.drevmassapp.data.model.CourseDetailsDto

sealed interface CourseDetailState {
    object Initial : CourseDetailState
    object Loading : CourseDetailState
    data class Failure(val message: String) : CourseDetailState
    data class Success(val courseDetail: CourseDetailsDto) : CourseDetailState
}