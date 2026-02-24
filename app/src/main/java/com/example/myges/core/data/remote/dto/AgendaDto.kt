package com.example.myges.core.data.remote.dto

import com.example.myges.core.domain.model.AgendaEvent
import com.google.gson.annotations.SerializedName

data class AgendaEventDto(
    @SerializedName("start_date") val startDate: Long?,
    @SerializedName("end_date") val endDate: Long?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("rooms") val rooms: List<String>?,
    @SerializedName("teachers") val teachers: List<String>?,
    @SerializedName("type") val type: String?
) {
    fun toDomain() = AgendaEvent(
        startDate = startDate ?: 0L,
        endDate = endDate ?: 0L,
        name = name.orEmpty(),
        description = description,
        rooms = rooms.orEmpty(),
        teachers = teachers.orEmpty(),
        type = type
    )
}
