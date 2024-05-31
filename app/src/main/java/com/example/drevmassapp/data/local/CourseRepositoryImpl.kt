package com.example.drevmassapp.data.local

import com.example.drevmassapp.data.model.CourseDtotem
import com.example.drevmassapp.data.remote.DrevMassApi
import com.example.drevmassapp.domain.repository.CourseRepository
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val api: DrevMassApi
): CourseRepository {
    override suspend fun getCourseList(token: String): List<CourseDtotem> {
        return api.getCourseList(getToken(token))
    }

    private fun getToken(token: String): String {
        return "Bearer $token"
    }

}