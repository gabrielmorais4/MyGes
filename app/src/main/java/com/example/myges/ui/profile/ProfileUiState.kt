package com.example.myges.ui.profile

import com.example.myges.core.domain.model.Profile

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(val profile: Profile) : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}
