package com.example.myges.core.domain.usecase

import com.example.myges.core.domain.model.NewsItem
import com.example.myges.core.domain.repository.GesRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val gesRepository: GesRepository
) {
    suspend operator fun invoke(page: Int = 1): List<NewsItem> =
        gesRepository.getNews(page)
}
