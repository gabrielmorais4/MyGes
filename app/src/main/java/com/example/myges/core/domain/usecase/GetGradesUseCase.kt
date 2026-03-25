package com.example.myges.core.domain.usecase

import com.example.myges.core.domain.model.GradesData
import com.example.myges.core.domain.model.Semester
import com.example.myges.core.domain.repository.GesRepository
import com.example.myges.util.AcademicYearCalculator
import javax.inject.Inject

class GetGradesUseCase @Inject constructor(
    private val gesRepository: GesRepository
) {
    suspend operator fun invoke(year: Int = AcademicYearCalculator.currentYear()): GradesData {
        val flatGrades = gesRepository.getGrades(year)
        val numberRegex = Regex("""\d+""")
        val semesters = flatGrades
            .groupBy { it.trimesterName.orEmpty() }
            .map { (name, courses) ->
                Semester(
                    number = numberRegex.find(name)?.value?.toIntOrNull(),
                    courses = courses
                )
            }
            .sortedBy { it.number }
        return GradesData(semesters = semesters)
    }
}
