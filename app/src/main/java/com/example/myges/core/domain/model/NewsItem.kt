package com.example.myges.core.domain.model

data class NewsItem(
    val id: String,
    val title: String,
    val text: String?,
    val date: String?,
    val authorName: String?,
    val imageUrl: String?
)
