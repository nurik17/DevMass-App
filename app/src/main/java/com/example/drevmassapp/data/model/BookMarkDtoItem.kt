package com.example.drevmassapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BookMarkDto(
    @SerializedName("course_id") val courseId: Int,
    @SerializedName("course_name") val courseName: String,
    @SerializedName("lessons") val lessons: List<Lesson>
)