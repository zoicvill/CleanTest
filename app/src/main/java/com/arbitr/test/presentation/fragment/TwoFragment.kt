package com.arbitr.test.presentation.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arbitr.test.databinding.FragmentTwoBinding
import com.arbitr.test.presentation.adapter.HistoryAdapter
import com.arbitr.test.presentation.viewmodel.ReViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class TwoFragment : Fragment() {

    private var binding: FragmentTwoBinding? = null
    private val mBinding get() = binding!!

    private val viewModel: ReViewModel by viewModels()

    private val histAdapter by lazy { HistoryAdapter() }

    private var click = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTwoBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timer()
        startRandom()
        setRecyclerView()
        barDataLoading()
    }

    /*timer*/
    private fun timer() = mBinding.run {
        val mHours = TimeUnit.MINUTES.toMillis(60)
        val countDownTimer = object : CountDownTimer(mHours, 1000) {
            override fun onTick(millis: Long) {
                val h = TimeUnit.MILLISECONDS.toSeconds(millis) / (60 * 60) % 24
                val m = TimeUnit.MILLISECONDS.toSeconds(millis) / 60 % 60
                val s = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
                includeTimer.hours.text = String.format("%02d", h)
                includeTimer.minutes.text = String.format("%02d", m)
                includeTimer.seconds.text = String.format("%02d", s)
            }

            override fun onFinish() {

            }
        }
        countDownTimer.start()
    }

    /*progressBar random */

    private fun startRandom() {
        mBinding.buttonRandom.setOnClickListener {
            lifecycleScope.launch {
                if (!click) {
                    click = true
                    random()
                } else {
                    click = false
                    viewModel.cancel()
                    progressO()
                }
            }

        }
    }

    private suspend fun random() = mBinding.includeRandom.run {
        lifecycleScope.launchWhenStarted {
            viewModel.rand()
        }
        lifecycleScope.launchWhenStarted {
            viewModel.firstRand.collect {
                progressBar.progress = it
                textViewPercent.text = "$it%"
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.secRand.collect {
                progressBar2.progress = it
                textViewpercent2.text = "$it%"
            }
        }

    }

    private fun progressO() = mBinding.includeRandom.run {

        progressBar.progress = 0
        textViewPercent.text = "0%"
        progressBar.invalidate()

        progressBar2.progress = 0
        textViewpercent2.text = "0%"
        progressBar2.invalidate()

    }

    /*histAdapter*/

    private fun setRecyclerView() {
        mBinding.recyclerrer.apply {
            adapter = histAdapter
        }

    }

    private fun barDataLoading() {
        lifecycleScope.launchWhenStarted {
            viewModel.start()
        }
        viewModel.isDataLoading.observe(viewLifecycleOwner) {
            if (it) {
                lifecycleScope.launchWhenStarted {
                    viewModel.bufferLoading.collect {

                        mBinding.progressBarLoadData.progress = it
                        mBinding.textViewPercentLoad.text = "$it%"
                    }

                }

            } else {
                setInfAdapter()
            }
        }

    }


    private fun setInfAdapter() {
        lifecycleScope.launchWhenStarted {
            viewModel.detailListDrink.collect {
                histAdapter.submitList(it)
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}