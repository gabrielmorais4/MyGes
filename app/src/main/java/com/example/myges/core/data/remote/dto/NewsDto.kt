package com.example.myges.core.data.remote.dto

import com.example.myges.core.domain.model.NewsItem
import com.google.gson.annotations.SerializedName

data class NewsItemDto(
    @SerializedName("id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("text") val text: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("author_name") val authorName: String?,
    @SerializedName("image_url") val imageUrl: String?
) {
    fun toDomain() = NewsItem(
        id = id.orEmpty(),
        title = title.orEmpty(),
        text = text,
        date = date,
        authorName = authorName,
        imageUrl = imageUrl
    )
}

data class NewsBannerDto(
    @SerializedName("id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("image_url") val imageUrl: String?
)
