package com.example.drevmassapp.data.model

import com.google.gson.annotations.SerializedName

data class ProductDetailItemDto(
    @SerializedName("basket_count") val basketCount: Int,
    @SerializedName("description") val description: String,
    @SerializedName("height") val height: String,
    @SerializedName("id") val id: Int,
    @SerializedName("image_src") val imageSrc: String,
    @SerializedName("price") val price: Int,
    @SerializedName("size") val size: String,
    @SerializedName("title") val title: String,
    @SerializedName("video_src") val videoSrc: String,
    @SerializedName("viewed") val viewed: Int
)