package com.example.myges.ui.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myges.core.domain.usecase.GetAgendaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val getAgendaUseCase: GetAgendaUseCase
) : ViewModel() {

    private val _weekOffset = MutableStateFlow(0)
    val weekOffset: StateFlow<Int> = _weekOffset.asStateFlow()

    private val _uiState = MutableStateFlow<AgendaUiState>(AgendaUiState.Loading)
    val uiState: StateFlow<AgendaUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun previousWeek() {
        _weekOffset.value = _weekOffset.value - 1
        load()
    }

    fun nextWeek() {
        _weekOffset.value = _weekOffset.value + 1
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = AgendaUiState.Loading
            runCatching { getAgendaUseCase(_weekOffset.value) }
                .onSuccess { _uiState.value = AgendaUiState.Success(it) }
                .onFailure { _uiState.value = AgendaUiState.Error(it.message ?: "") }
        }
    }
}
