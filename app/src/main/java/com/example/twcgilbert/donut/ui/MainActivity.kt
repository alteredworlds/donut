package com.example.twcgilbert.donut.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.example.twcgilbert.donut.R
import com.example.twcgilbert.donut.common.ui.BaseActivity
import com.example.twcgilbert.donut.databinding.MainActivityBinding

import javax.inject.Inject

class MainActivity : BaseActivity(), MainActivityContract.View {

    @Inject
    lateinit var viewModel: MainActivityContract.ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<MainActivityBinding>(
                this,
                R.layout.main_activity)
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        viewModel.onPause()
        super.onPause()
    }
}
