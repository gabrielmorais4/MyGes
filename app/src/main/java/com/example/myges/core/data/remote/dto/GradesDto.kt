package com.example.myges.core.data.remote.dto

import com.example.myges.core.domain.model.CourseGrade
import com.google.gson.annotations.SerializedName

data class CourseGradeDto(
    @SerializedName("course") val course: String?,
    @SerializedName("grades") val grades: List<Double>?,
    @SerializedName("exam") val exam: Double?,
    @SerializedName("average") val average: Double?,
    @SerializedName("trimester_name") val trimesterName: String?,
    @SerializedName("coef") val coef: String?,
    @SerializedName("ects") val ects: String?,
    @SerializedName("ccaverage") val ccAverage: Double?,
    @SerializedName("letter_mark") val letterMark: String?,
    @SerializedName("absences") val absences: Int?
) {
    fun toDomain() = CourseGrade(
        name = course.orEmpty(),
        ccGrades = grades.orEmpty(),
        ccAverage = ccAverage,
        exam = exam,
        average = average,
        coef = coef,
        ects = ects,
        letterMark = letterMark,
        absences = absences ?: 0
    )
}
