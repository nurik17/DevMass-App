package com.example.drevmassapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class CourseStartDto(
    @SerializedName("course_progress") val courseProgress: Boolean
)