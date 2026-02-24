package com.example.myges.ui.grades

import com.example.myges.core.domain.model.GradesData

sealed interface GradesUiState {
    data object Loading : GradesUiState
    data class Success(val data: GradesData) : GradesUiState
    data class Error(val message: String) : GradesUiState
}
