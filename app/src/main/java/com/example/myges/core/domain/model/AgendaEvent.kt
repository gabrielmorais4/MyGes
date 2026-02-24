package com.example.myges.core.domain.model

data class AgendaEvent(
    val startDate: Long,
    val endDate: Long,
    val name: String,
    val description: String?,
    val rooms: List<String>,
    val teachers: List<String>,
    val type: String?
)
