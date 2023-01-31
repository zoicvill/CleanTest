package com.arbitr.test.domain.repository

import com.arbitr.test.core.DataState
import com.arbitr.test.data.network.model.Ratings
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getRaitings(): Flow<DataState<List<Ratings>>>
}