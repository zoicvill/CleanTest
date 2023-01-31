package com.arbitr.test.data.network.datasourse

import com.arbitr.test.data.network.model.Ratings

interface ApiDataSource {
    suspend fun getList(): List<Ratings>
}