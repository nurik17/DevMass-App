package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.data.model.BookMarkDto
import com.example.drevmassapp.data.model.CourseDetailsDto
import com.example.drevmassapp.data.model.CourseDtotem
import com.example.drevmassapp.data.model.CourseStartDto
import com.example.drevmassapp.data.model.Lesson
import com.example.drevmassapp.data.model.LessonCompleteDto

interface CourseRepository {
    suspend fun getCourseList(token: String): List<CourseDtotem>
    suspend fun getCourseById(token: String,courseId: Int): CourseDetailsDto
    suspend fun getLessonsById(token: String,courseId: Int): List<Lesson>
    suspend fun getLessonsDetailById(token: String,courseId: Int,lessonId: Int): Lesson
    suspend fun startCourse(token: String,courseId: Int): CourseStartDto
    suspend fun lessonComplete(token: String,lessonId: Int,courseId: Int): LessonCompleteDto

    suspend fun getBookMarks(token: String): List<BookMarkDto>
}