package com.iamnaran.firefly.di

import com.iamnaran.firefly.data.repository.auth.AuthRepository
import com.iamnaran.firefly.data.repository.auth.AuthRepositoryImpl
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
    abstract fun bindUserRepository(authRepository: AuthRepositoryImpl): AuthRepository
}