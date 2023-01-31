package com.arbitr.test.di

import com.arbitr.test.data.network.datasourse.ApiDataSource
import com.arbitr.test.data.repository.RepositoryImpl
import com.arbitr.test.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCocktailRepository(
        lanApi: ApiDataSource,
    ): Repository {
        return RepositoryImpl(api = lanApi)
    }
}