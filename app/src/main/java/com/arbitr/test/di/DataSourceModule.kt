package com.arbitr.test.di

import com.arbitr.test.data.network.DataServiceApi
import com.arbitr.test.data.network.datasourse.ApiDataSource
import com.arbitr.test.data.network.datasourse.ApiDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {


    @Provides
    @Singleton
    fun provideApiDataSource(api: DataServiceApi): ApiDataSource {
        return ApiDataSourceImpl(api)
    }
}