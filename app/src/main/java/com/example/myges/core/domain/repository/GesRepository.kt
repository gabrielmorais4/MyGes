package com.example.myges.core.domain.repository

import com.example.myges.core.domain.model.Absence
import com.example.myges.core.domain.model.AgendaEvent
import com.example.myges.core.domain.model.GradesData
import com.example.myges.core.domain.model.NewsItem
import com.example.myges.core.domain.model.Profile

interface GesRepository {
    suspend fun getProfile(): Profile
    suspend fun getAgenda(start: Long, end: Long): List<AgendaEvent>
    suspend fun getNews(page: Int): List<NewsItem>
    suspend fun getGrades(year: Int): GradesData
    suspend fun getAbsences(year: Int): List<Absence>
}
