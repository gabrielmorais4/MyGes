package com.example.myges.core.domain.model

data class GradeEntry(
    val name: String?,
    val grade: Double?,
    val coefficient: Double?,
    val date: String?,
    val isAbsence: Boolean
)

data class CourseGrades(
    val name: String,
    val average: Double?,
    val coefficient: Double?,
    val grades: List<GradeEntry>
)

data class Semester(
    val name: String,
    val average: Double?,
    val courses: List<CourseGrades>
)

data class GradesData(
    val semesters: List<Semester>
)
