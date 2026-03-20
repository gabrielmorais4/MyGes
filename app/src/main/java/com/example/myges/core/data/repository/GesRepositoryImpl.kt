package com.example.myges.core.data.repository

import com.example.myges.core.data.remote.api.GesApi
import com.example.myges.core.domain.model.Absence
import com.example.myges.core.domain.model.AgendaEvent
import com.example.myges.core.domain.model.GradesData
import com.example.myges.core.domain.model.NewsItem
import com.example.myges.core.domain.model.Profile
import com.example.myges.core.domain.model.Semester
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

    override suspend fun getGrades(year: Int): GradesData {
        val flat = gesApi.getGrades(year).result
        val semesters = flat
            .groupBy { it.trimesterName.orEmpty() }
            .map { (name, courses) ->
                Semester(
                    name = name,
                    courses = courses.map { it.toDomain() }
                )
            }
            .sortedBy { it.name }
        return GradesData(semesters = semesters)
    }

    override suspend fun getAbsences(year: Int): List<Absence> =
        gesApi.getAbsences(year).result.map { it.toDomain() }
}
