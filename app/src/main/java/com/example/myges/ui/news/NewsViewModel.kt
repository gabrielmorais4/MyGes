package com.example.myges.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myges.core.domain.usecase.GetNewsBannersUseCase
import com.example.myges.core.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getNewsBannersUseCase: GetNewsBannersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading
            runCatching {
                val bannersDeferred = async { getNewsBannersUseCase() }
                val newsDeferred = async { getNewsUseCase() }
                Pair(bannersDeferred.await(), newsDeferred.await())
            }
                .onSuccess { (banners, items) ->
                    _uiState.value = NewsUiState.Success(banners = banners, items = items)
                }
                .onFailure { _uiState.value = NewsUiState.Error(it.message ?: "") }
        }
    }
}
