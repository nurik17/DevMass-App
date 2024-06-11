package com.example.drevmassapp.data.local

import com.example.drevmassapp.data.model.BookMarkDto
import com.example.drevmassapp.data.model.CourseDetailsDto
import com.example.drevmassapp.data.model.CourseDtotem
import com.example.drevmassapp.data.model.CourseStartDto
import com.example.drevmassapp.data.model.Lesson
import com.example.drevmassapp.data.model.LessonCompleteDto
import com.example.drevmassapp.data.remote.DrevMassApi
import com.example.drevmassapp.domain.repository.CourseRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val api: DrevMassApi
): CourseRepository {
    override suspend fun getCourseList(token: String): List<CourseDtotem> {
        delay(2500L)
        return api.getCourseList(getToken(token))
    }

    override suspend fun getCourseById(token: String, courseId: Int): CourseDetailsDto {
        return api.getCourseById(getToken(token),courseId)
    }

    override suspend fun getLessonsById(token: String, courseId: Int): List<Lesson> {
        return api.getLessonsById(getToken(token),courseId)
    }

    override suspend fun getLessonsDetailById(token: String, courseId: Int, lessonId: Int): Lesson {
        return api.getLessonsDetailById(getToken(token),courseId, lessonId)
    }

    override suspend fun startCourse(token: String, courseId: Int): CourseStartDto {
        return api.startCourse(getToken(token),courseId)
    }

    override suspend fun lessonComplete(
        token: String,
        lessonId: Int,
        courseId: Int
    ): LessonCompleteDto {
        return api.lessonComplete(getToken(token),lessonId,courseId)
    }

    override suspend fun getBookMarks(token: String): List<BookMarkDto> {
        delay(1000L)
        return api.getBookMarks(getToken(token))
    }

    private fun getToken(token: String): String {
        return "Bearer $token"
    }

}