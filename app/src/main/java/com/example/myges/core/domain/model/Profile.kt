package com.example.myges.core.domain.model

data class Profile(
    val uid: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val photoUrl: String?
) {
    val fullName: String get() = "$firstname $lastname"
}
