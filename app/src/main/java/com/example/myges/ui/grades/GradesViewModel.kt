package com.example.myges.ui.grades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myges.core.domain.usecase.GetGradesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GradesViewModel @Inject constructor(
    private val getGradesUseCase: GetGradesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<GradesUiState>(GradesUiState.Loading)
    val uiState: StateFlow<GradesUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = GradesUiState.Loading
            runCatching { getGradesUseCase() }
                .onSuccess { _uiState.value = GradesUiState.Success(it) }
                .onFailure { _uiState.value = GradesUiState.Error(it.message ?: "") }
        }
    }
}
