package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.data.model.CourseDtotem

interface CourseRepository {
    suspend fun getCourseList(token: String): List<CourseDtotem>
}