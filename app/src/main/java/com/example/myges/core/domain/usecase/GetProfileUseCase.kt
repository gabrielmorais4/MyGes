package com.example.myges.core.domain.usecase

import com.example.myges.core.domain.model.Profile
import com.example.myges.core.domain.repository.GesRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val gesRepository: GesRepository
) {
    suspend operator fun invoke(): Profile = gesRepository.getProfile()
}
