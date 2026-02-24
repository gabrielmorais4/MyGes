package com.example.myges.core.data.remote.dto

import com.example.myges.core.domain.model.CourseGrades
import com.example.myges.core.domain.model.GradeEntry
import com.example.myges.core.domain.model.GradesData
import com.example.myges.core.domain.model.Semester
import com.google.gson.annotations.SerializedName

data class GradeEntryDto(
    @SerializedName("grade") val grade: Double?,
    @SerializedName("coef") val coefficient: Double?,
    @SerializedName("name") val name: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("absence") val absence: Boolean?
) {
    fun toDomain() = GradeEntry(
        name = name,
        grade = grade,
        coefficient = coefficient,
        date = date,
        isAbsence = absence ?: false
    )
}

data class CourseGradesDto(
    @SerializedName("name") val name: String?,
    @SerializedName("average") val average: Double?,
    @SerializedName("coef") val coefficient: Double?,
    @SerializedName("grades") val grades: List<GradeEntryDto>?
) {
    fun toDomain() = CourseGrades(
        name = name.orEmpty(),
        average = average,
        coefficient = coefficient,
        grades = grades?.map { it.toDomain() }.orEmpty()
    )
}

data class SemesterDto(
    @SerializedName("name") val name: String?,
    @SerializedName("average") val average: Double?,
    @SerializedName("courses") val courses: List<CourseGradesDto>?
) {
    fun toDomain() = Semester(
        name = name.orEmpty(),
        average = average,
        courses = courses?.map { it.toDomain() }.orEmpty()
    )
}

data class GradesResponseDto(
    @SerializedName("semestres") val semesters: List<SemesterDto>?
) {
    fun toDomain() = GradesData(
        semesters = semesters?.map { it.toDomain() }.orEmpty()
    )
}
