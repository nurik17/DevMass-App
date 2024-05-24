package com.example.drevmassapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Recommend(
    @SerializedName("basket_count")val basketCount: Int,
    @SerializedName("description")val description: String,
    @SerializedName("height")val height: String,
    @SerializedName("id")val id: Int,
    @SerializedName("image_src")val image_src: String,
    @SerializedName("price")val price: Int,
    @SerializedName("size")val size: String,
    @SerializedName("title")val title: String,
    @SerializedName("video_src")val videoSrc: String
)