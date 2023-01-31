package com.arbitr.test.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbitr.test.core.DataState
import com.arbitr.test.data.network.model.Ratings
import com.arbitr.test.domain.usecases.RatApiUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ReViewModel @Inject constructor(
    private val api: RatApiUseCases,
) : ViewModel() {

    private var oneJob: Job? = null
    private var twoJob: Job? = null
    private var loadJob: Job? = null

    private val mutListHistory: MutableStateFlow<List<Ratings>> = MutableStateFlow(listOf())
    val detailListDrink = mutListHistory.asStateFlow()

    private val mutIsDataLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isDataLoading: LiveData<Boolean> = mutIsDataLoading

    private val mutBufferLoading: MutableStateFlow<Int> = MutableStateFlow(0)
    val bufferLoading: StateFlow<Int> = mutBufferLoading


    private val mutIsError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = mutIsError


    private val mutBuffer: MutableStateFlow<Int> = MutableStateFlow(0)
    val buffer: StateFlow<Int> = mutBuffer

    private val mutFlag: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val flag: StateFlow<Boolean> = mutFlag



    private val mutFirstRand: MutableStateFlow<Int> = MutableStateFlow(0)
    val firstRand: StateFlow<Int> = mutFirstRand

    private val mutSecRand: MutableStateFlow<Int> = MutableStateFlow(0)
    val secRand: StateFlow<Int> = mutSecRand


    fun flagEmi(boolean: Boolean) {
        Log.d("lol", "ReViewModel flagEmi $boolean")
        mutFlag.value = boolean
    }

    suspend fun rand() {
        oneJob = viewModelScope.launch {
            for (i in 0..100) {
                mutFirstRand.value = i
                delay(Random.nextLong(50, 250))
                Log.d("lol", "random() ${i}")
                if (i == 100) {
                    oneJob?.cancel()
                }
            }
        }

        twoJob = viewModelScope.launch {
            for (i in 0..100) {
                mutSecRand.value = i
                delay(Random.nextLong(50, 500))
                if (i == 100) {
                    twoJob?.cancel()
                }
            }
        }
    }

    suspend fun cancel() {
        twoJob?.cancelAndJoin()
        oneJob?.cancelAndJoin()
        oneJob = null
        twoJob = null
    }


    fun timer() {
        viewModelScope.launch {
            Log.d("lol", "ReViewModel timer() ${flag.value}")
            for (i in mutBuffer.value..100) {
                if (flag.value) {
                    break
                }
                mutBuffer.value = i
                delay(140)
            }
        }
    }


    fun start() {
        api.getRaitings().onEach { api ->
            when (api) {
                is DataState.Error -> api.exception.also {
                    mutIsDataLoading.value = false
                    mutIsError.value = true
                    Log.d("lol", "start() Error ${it.message}")
                }
                DataState.Loading -> {
                    mutIsDataLoading.postValue(true)
                    loader()
                }
                is DataState.Success -> {
                    mutListHistory.value = api.data
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loader() {
        loadJob = viewModelScope.launch {
            for (i in 0..100) {
                mutBufferLoading.value = i
                delay(10)
            }
            if (mutBufferLoading.value >= 100) {
                loadJob?.cancel()
                mutIsDataLoading.postValue(false)
            }

        }
    }
}