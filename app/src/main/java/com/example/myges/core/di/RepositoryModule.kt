package com.example.myges.core.di

import com.example.myges.core.data.repository.AuthRepositoryImpl
import com.example.myges.core.data.repository.GesRepositoryImpl
import com.example.myges.core.domain.repository.AuthRepository
import com.example.myges.core.domain.repository.GesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindGesRepository(impl: GesRepositoryImpl): GesRepository
}
