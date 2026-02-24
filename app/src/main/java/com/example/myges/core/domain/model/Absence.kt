package com.example.myges.core.domain.model

data class Absence(
    val date: String?,
    val reason: String?,
    val justified: Boolean,
    val courseId: String?,
    val courseName: String?
)
