package com.example.myges.core.data.repository

import com.example.myges.core.data.remote.api.GesApi
import com.example.myges.core.domain.model.Absence
import com.example.myges.core.domain.model.AgendaEvent
import com.example.myges.core.domain.model.CourseGrade
import com.example.myges.core.domain.model.NewsItem
import com.example.myges.core.domain.model.Profile
import com.example.myges.core.domain.repository.GesRepository
import javax.inject.Inject

class GesRepositoryImpl @Inject constructor(
    private val gesApi: GesApi
) : GesRepository {

    override suspend fun getProfile(): Profile =
        gesApi.getProfile().result.toDomain()

    override suspend fun getAgenda(start: Long, end: Long): List<AgendaEvent> =
        gesApi.getAgenda(start, end).result.map { it.toDomain() }

    override suspend fun getNews(page: Int): List<NewsItem> =
        gesApi.getNews(page).result.content.orEmpty().map { it.toDomain() }

    override suspend fun getGrades(year: Int): List<CourseGrade> =
        gesApi.getGrades(year).result.map { it.toDomain() }

    override suspend fun getAbsences(year: Int): List<Absence> =
        gesApi.getAbsences(year).result.map { it.toDomain() }
}
