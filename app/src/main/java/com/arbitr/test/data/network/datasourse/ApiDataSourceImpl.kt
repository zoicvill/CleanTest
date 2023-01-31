package com.arbitr.test.data.network.datasourse

import com.arbitr.test.core.toDomain
import com.arbitr.test.data.network.DataServiceApi
import com.arbitr.test.data.network.model.Ratings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiDataSourceImpl @Inject constructor(
    private val api: DataServiceApi
) : ApiDataSource {
    override suspend fun getList(): List<Ratings> {
        return api.getDataService().toDomain()
    }
}