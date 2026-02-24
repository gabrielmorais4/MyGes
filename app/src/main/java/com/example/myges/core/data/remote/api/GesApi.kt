package com.example.myges.core.data.remote.api

import com.example.myges.core.data.remote.dto.AbsenceDto
import com.example.myges.core.data.remote.dto.AgendaEventDto
import com.example.myges.core.data.remote.dto.ApiResponse
import com.example.myges.core.data.remote.dto.GradesResponseDto
import com.example.myges.core.data.remote.dto.NewsBannerDto
import com.example.myges.core.data.remote.dto.NewsItemDto
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
    suspend fun getNews(@Query("page") page: Int = 1): ApiResponse<List<NewsItemDto>>

    @GET("me/{year}/grades")
    suspend fun getGrades(@Path("year") year: Int): ApiResponse<GradesResponseDto>

    @GET("me/{year}/absences")
    suspend fun getAbsences(@Path("year") year: Int): ApiResponse<List<AbsenceDto>>

    @GET("me/{year}/teachers")
    suspend fun getTeachers(@Path("year") year: Int): ApiResponse<List<Any>>

    @GET("me/{year}/classes")
    suspend fun getClasses(@Path("year") year: Int): ApiResponse<List<Any>>

    @GET("me/classes/{classId}/students")
    suspend fun getStudents(@Path("classId") classId: String): ApiResponse<List<Any>>

    @GET("me/students/{studentId}")
    suspend fun getStudent(@Path("studentId") studentId: String): ApiResponse<Any>

    @GET("me/{year}/courses")
    suspend fun getCourses(@Path("year") year: Int): ApiResponse<List<Any>>
}
