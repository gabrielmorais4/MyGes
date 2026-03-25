package com.example.myges.core.domain.model

data class CourseGrade(
    val name: String,
    val trimesterName: String?,
    val ccGrades: List<Double>,
    val ccAverage: Double?,
    val exam: Double?,
    val average: Double?,
    val coef: String?,
    val ects: String?,
    val letterMark: String?,
    val absences: Int
)

data class Semester(
    val number: Int?,
    val courses: List<CourseGrade>
)

data class GradesData(
    val semesters: List<Semester>
)
