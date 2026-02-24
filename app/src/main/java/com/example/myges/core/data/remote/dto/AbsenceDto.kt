package com.example.myges.core.data.remote.dto

import com.example.myges.core.domain.model.Absence
import com.google.gson.annotations.SerializedName

data class AbsenceDto(
    @SerializedName("date") val date: String?,
    @SerializedName("reason") val reason: String?,
    @SerializedName("justified") val justified: Boolean?,
    @SerializedName("course_id") val courseId: String?,
    @SerializedName("course_name") val courseName: String?
) {
    fun toDomain() = Absence(
        date = date,
        reason = reason,
        justified = justified ?: false,
        courseId = courseId,
        courseName = courseName
    )
}
