package com.arbitr.test.data.network


import com.arbitr.test.data.network.model.ResponseData
import retrofit2.http.GET

interface DataServiceApi {

    @GET("testAndroidData")
    suspend fun getDataService(): ResponseData
}