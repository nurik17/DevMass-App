package com.example.drevmassapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProductDetailDto(
    @SerializedName("Product") val product: ProductDetailItemDto,
    @SerializedName("Recommend") val recommend: List<Recommend>
)