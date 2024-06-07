package com.example.drevmassapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Lesson(
    @SerializedName("CourseID") val courseId: Int,
    @SerializedName("CourseTitle") val courseTitle: String,
    @SerializedName("completed") val completed: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("image_src") val imageSrc: String,
    @SerializedName("is_favorite") val isFavorite: Boolean,
    @SerializedName("name") val name: String,
    @SerializedName("order_id") val orderId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("used_products") val usedProducts: List<Product>,
    @SerializedName("video_src") val videoSrc: String
)