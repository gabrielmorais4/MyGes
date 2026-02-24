package com.example.myges.ui.news

import com.example.myges.core.domain.model.NewsItem

sealed interface NewsUiState {
    data object Loading : NewsUiState
    data class Success(val items: List<NewsItem>) : NewsUiState
    data class Error(val message: String) : NewsUiState
}
