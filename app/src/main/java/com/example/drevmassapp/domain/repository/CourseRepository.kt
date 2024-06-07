package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.data.model.CourseDetailsDto
import com.example.drevmassapp.data.model.CourseDtotem
import com.example.drevmassapp.data.model.Lesson

interface CourseRepository {
    suspend fun getCourseList(token: String): List<CourseDtotem>
    suspend fun getCourseById(token: String,courseId: Int): CourseDetailsDto
    suspend fun getLessonsById(token: String,courseId: Int): List<Lesson>
    suspend fun getLessonsDetailById(token: String,courseId: Int,lessonId: Int): Lesson
}