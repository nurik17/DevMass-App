package com.example.drevmassapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LessonCompleteDto(
    @SerializedName("completed") val completed: Boolean
)