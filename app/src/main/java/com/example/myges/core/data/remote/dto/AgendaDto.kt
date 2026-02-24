package com.example.myges.core.data.remote.dto

import com.example.myges.core.domain.model.AgendaEvent
import com.google.gson.annotations.SerializedName

data class RoomDto(
    @SerializedName("name") val name: String?,
    @SerializedName("campus") val campus: String?,
    @SerializedName("floor") val floor: String?
)

data class AgendaEventDto(
    @SerializedName("start_date") val startDate: Long?,
    @SerializedName("end_date") val endDate: Long?,
    @SerializedName("name") val name: String?,
    @SerializedName("comment") val comment: String?,
    @SerializedName("rooms") val rooms: List<RoomDto>?,
    @SerializedName("teacher") val teacher: String?,
    @SerializedName("type") val type: String?
) {
    fun toDomain() = AgendaEvent(
        startDate = startDate ?: 0L,
        endDate = endDate ?: 0L,
        name = name.orEmpty(),
        description = comment,
        rooms = rooms?.mapNotNull { it.name }.orEmpty(),
        teacher = teacher?.trim()?.takeIf { it.isNotBlank() },
        type = type
    )
}
