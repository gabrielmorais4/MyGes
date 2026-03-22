package com.example.myges.core.data.remote.dto

import com.example.myges.core.domain.model.CourseGrade
import com.google.gson.annotations.SerializedName

data class CourseGradeDto(
    @SerializedName("course") val course: String?,
    @SerializedName("grades") val grades: List<Any?>?,
    @SerializedName("exam") val exam: Any?,
    @SerializedName("average") val average: Any?,
    @SerializedName("trimester_name") val trimesterName: String?,
    @SerializedName("coef") val coef: String?,
    @SerializedName("ects") val ects: String?,
    @SerializedName("ccaverage") val ccAverage: Any?,
    @SerializedName("letter_mark") val letterMark: String?,
    @SerializedName("absences") val absences: Any?
) {
    fun toDomain() = CourseGrade(
        name = course.orEmpty(),
        trimesterName = trimesterName,
        ccGrades = grades.orEmpty().mapNotNull { it.toDoubleOrNull() },
        ccAverage = ccAverage.toDoubleOrNull(),
        exam = exam.toDoubleOrNull(),
        average = average.toDoubleOrNull(),
        coef = coef,
        ects = ects,
        letterMark = letterMark,
        absences = absences.toIntOrNull() ?: 0
    )
}

private fun Any?.toDoubleOrNull(): Double? = when (this) {
    is Number -> toDouble()
    is String -> replace(',', '.').toDoubleOrNull()
    else -> null
}

private fun Any?.toIntOrNull(): Int? = when (this) {
    is Number -> toInt()
    is String -> toIntOrNull()
    else -> null
}
