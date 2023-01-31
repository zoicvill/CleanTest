package com.arbitr.test.domain.usecases

import com.arbitr.test.core.DataState
import com.arbitr.test.data.network.model.Ratings
import com.arbitr.test.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RatApiUseCases @Inject constructor(private val rep: Repository) {
    fun getRaitings(): Flow<DataState<List<Ratings>>> {
        return rep.getRaitings()
    }
}