package com.example.myges.util

import java.time.LocalDate

object AcademicYearCalculator {
    fun currentYear(): Int {
        val today = LocalDate.now()
        return if (today.monthValue >= 9) today.year else today.year - 1
    }
}
