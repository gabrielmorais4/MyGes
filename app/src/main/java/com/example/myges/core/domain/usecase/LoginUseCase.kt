package com.example.myges.core.domain.usecase

import com.example.myges.core.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<Unit> =
        authRepository.login(username, password)
}
