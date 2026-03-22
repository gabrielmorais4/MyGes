package com.example.myges.core.data.remote.api

import com.example.myges.core.data.remote.dto.AbsenceDto
import com.example.myges.core.data.remote.dto.AgendaEventDto
import com.example.myges.core.data.remote.dto.ApiResponse
import com.example.myges.core.data.remote.dto.CourseGradeDto
import com.example.myges.core.data.remote.dto.NewsBannerDto
import com.example.myges.core.data.remote.dto.NewsPageDto
import com.example.myges.core.data.remote.dto.ProfileDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GesApi {

    @GET("me/profile")
    suspend fun getProfile(): ApiResponse<ProfileDto>

    @GET("me/agenda")
    suspend fun getAgenda(
        @Query("start") start: Long,
        @Query("end") end: Long
    ): ApiResponse<List<AgendaEventDto>>

    @GET("me/news/banners")
    suspend fun getNewsBanners(): ApiResponse<List<NewsBannerDto>>

    @GET("me/news")
    suspend fun getNews(@Query("page") page: Int = 1): ApiResponse<NewsPageDto>

    @GET("me/{year}/grades")
    suspend fun getGrades(@Path("year") year: Int): ApiResponse<List<CourseGradeDto>>

    @GET("me/{year}/absences")
    suspend fun getAbsences(@Path("year") year: Int): ApiResponse<List<AbsenceDto>>
}
