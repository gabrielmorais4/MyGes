package com.example.myges.ui.agenda

import com.example.myges.core.domain.model.AgendaEvent

sealed interface AgendaUiState {
    data object Loading : AgendaUiState
    data class Success(val events: List<AgendaEvent>) : AgendaUiState
    data class Error(val message: String) : AgendaUiState
}
