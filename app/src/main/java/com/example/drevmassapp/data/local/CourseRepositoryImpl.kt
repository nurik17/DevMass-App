package com.example.drevmassapp.data.local

import com.example.drevmassapp.data.model.CourseDetailsDto
import com.example.drevmassapp.data.model.CourseDtotem
import com.example.drevmassapp.data.model.Lesson
import com.example.drevmassapp.data.remote.DrevMassApi
import com.example.drevmassapp.domain.repository.CourseRepository
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val api: DrevMassApi
): CourseRepository {
    override suspend fun getCourseList(token: String): List<CourseDtotem> {
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

    private fun getToken(token: String): String {
        return "Bearer $token"
    }

}