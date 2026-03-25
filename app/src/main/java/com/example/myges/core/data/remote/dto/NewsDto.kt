package com.example.myges.core.data.remote.dto

import com.example.myges.core.domain.model.NewsBanner
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
    @SerializedName("ba_id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("image") val imageUrl: String?,
    @SerializedName("author") val author: String?,
    @SerializedName("url") val url: String?
) {
    fun toDomain() = NewsBanner(
        id = id?.toString().orEmpty(),
        title = title.orEmpty(),
        imageUrl = imageUrl
    )
}

data class NewsBannerPageDto(
    @SerializedName("content") val content: List<NewsBannerDto>?
)

data class NewsPageDto(
    @SerializedName("content") val content: List<NewsItemDto>?
)
