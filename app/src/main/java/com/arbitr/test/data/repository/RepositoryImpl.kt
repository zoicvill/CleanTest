package com.arbitr.test.data.repository

import com.arbitr.test.core.DataState
import com.arbitr.test.data.network.datasourse.ApiDataSource
import com.arbitr.test.data.network.model.Ratings
import com.arbitr.test.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val api: ApiDataSource
): Repository{

    override fun getRaitings(): Flow<DataState<List<Ratings>>> = flow {
        emit(DataState.Loading)
        emit(DataState.Success(api.getList()))
    }.catch { emit(DataState.Error(it)) }
    .flowOn(Dispatchers.IO)

}