package com.example.myges.core.domain.repository

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<Unit>
    fun isLoggedIn(): Boolean
    fun logout()
}
