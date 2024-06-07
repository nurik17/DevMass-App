package com.example.drevmassapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CourseDetailsDto(
    @SerializedName("all_lessons") val allLessons: Int,
    @SerializedName("completed_lessons") val completedLessons: Int,
    @SerializedName("course") val course: CourseDtotem
)