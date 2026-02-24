package com.example.myges.core.domain.usecase

import com.example.myges.core.domain.model.GradesData
import com.example.myges.core.domain.repository.GesRepository
import com.example.myges.util.AcademicYearCalculator
import javax.inject.Inject

class GetGradesUseCase @Inject constructor(
    private val gesRepository: GesRepository
) {
    suspend operator fun invoke(year: Int = AcademicYearCalculator.currentYear()): GradesData =
        gesRepository.getGrades(year)
}
