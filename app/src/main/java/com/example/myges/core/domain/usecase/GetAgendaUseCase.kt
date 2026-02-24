package com.example.myges.core.domain.usecase

import com.example.myges.core.domain.model.AgendaEvent
import com.example.myges.core.domain.repository.GesRepository
import com.example.myges.util.AgendaRangeCalculator
import javax.inject.Inject

class GetAgendaUseCase @Inject constructor(
    private val gesRepository: GesRepository
) {
    suspend operator fun invoke(): List<AgendaEvent> {
        val (start, end) = AgendaRangeCalculator.calculateRange()
        return gesRepository.getAgenda(start, end)
    }
}
