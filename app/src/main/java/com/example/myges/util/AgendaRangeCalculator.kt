package com.example.myges.util

import java.time.YearMonth
import java.time.ZoneId

object AgendaRangeCalculator {

    fun calculateRange(): Pair<Long, Long> {
        val zone = ZoneId.systemDefault()
        val now = YearMonth.now()

        val start = now
            .atDay(1)
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()

        val end = now
            .plusMonths(2)
            .atDay(1)
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()

        return start to end
    }
}
