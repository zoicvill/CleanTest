package com.arbitr.test.presentation.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arbitr.test.R
import com.arbitr.test.databinding.FragmentFirstBinding
import com.arbitr.test.presentation.custom.CustomView
import com.arbitr.test.presentation.viewmodel.ReViewModel
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var binding: FragmentFirstBinding? = null
    private val mBinding get() = binding!!

    private lateinit var customDialogs: CustomView
    private lateinit var closeDialog: MaterialCardView

    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog


    private val viewModel: ReViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lottiePlay()
        customDialog()
        listener()
    }

    override fun onStart() {
        super.onStart()
        progressBarHead()
    }

    private fun progressBarHead() {
        lifecycleScope.launchWhenStarted {
            viewModel.flagEmi(false)
            viewModel.timer()
            viewModel.buffer.collect {
                mBinding.progressBar.progress = it
                mBinding.progressTextPercent.text = "$it%"
            }

        }
    }



    private fun listener() {
        mBinding.fragmentNext.setOnClickListener {
            this@FirstFragment.findNavController().navigate(
                FirstFragmentDirections.actionFirstFragmentToTwoFragment()
            )
            Log.d("lol", "fragmentNext $it")
        }

        mBinding.dialogButton.setOnClickListener {
            dialog.show()
        }

        closeDialog.setOnClickListener {
            dialog.cancel()

        }
    }


    private fun lottiePlay() = mBinding.includeLottieAnim.run {
        buttonStart.setOnClickListener {
            animationView.playAnimation()
        }
        buttonStop.setOnClickListener {
            animationView.pauseAnimation()
        }
        buttonHide.setOnClickListener {
            animationView.apply {
                visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
        }

    }

    private fun customDialog() {
        val view = this.layoutInflater.inflate(R.layout.custom_view, null)
        customDialogs = CustomView(requireContext())
        customDialogs = view.findViewById<View>(R.id.myView) as CustomView
        closeDialog = view.findViewById<View>(R.id.close) as MaterialCardView

        builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(view)
        dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onPause() {
        super.onPause()
        lifecycleScope.launchWhenStarted {
            Log.d("lol", "onStop()flagEmi true")
            viewModel.flagEmi(true)
            mBinding.includeLottieAnim.animationView.pauseAnimation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}