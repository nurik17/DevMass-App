package com.example.drevmassapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CourseDtotem(
    @SerializedName("bonus_info") val bonusInfo: BonusInfo,
    @SerializedName("bonus_type") val bonusType: String,
    @SerializedName("completed") val completed: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("image_src") val imageSrc: String,
    @SerializedName("is_started") val isStarted: Boolean,
    @SerializedName("lesson_cnt") val lessonCont: Int,
    @SerializedName("name") val name: String
)