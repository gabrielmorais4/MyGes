package com.example.myges.core.domain.repository

import com.example.myges.core.domain.model.Absence
import com.example.myges.core.domain.model.AgendaEvent
import com.example.myges.core.domain.model.CourseGrade
import com.example.myges.core.domain.model.NewsBanner
import com.example.myges.core.domain.model.NewsItem
import com.example.myges.core.domain.model.Profile

interface GesRepository {
    suspend fun getProfile(): Profile
    suspend fun getAgenda(start: Long, end: Long): List<AgendaEvent>
    suspend fun getNewsBanners(): List<NewsBanner>
    suspend fun getNews(page: Int): List<NewsItem>
    suspend fun getGrades(year: Int): List<CourseGrade>
    suspend fun getAbsences(year: Int): List<Absence>
}
