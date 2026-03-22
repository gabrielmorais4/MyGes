package com.example.myges.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId

object AgendaRangeCalculator {

    fun calculateRange(weekOffset: Int = 0): Pair<Long, Long> {
        val zone = ZoneId.systemDefault()
        val now = LocalDate.now()
            .with(DayOfWeek.MONDAY)
            .plusWeeks(weekOffset.toLong())

        val start = now
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()

        val end = now
            .plusDays(7)
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()

        return start to end
    }
}
