package com.example.myges.core.domain.usecase

import com.example.myges.core.domain.model.NewsBanner
import com.example.myges.core.domain.repository.GesRepository
import javax.inject.Inject

class GetNewsBannersUseCase @Inject constructor(
    private val gesRepository: GesRepository
) {
    suspend operator fun invoke(): List<NewsBanner> =
        gesRepository.getNewsBanners()
}
