package com.example.twcgilbert.donut.common.ui

import android.databinding.ObservableField

/**
 * Created by twcgilbert on 23/11/2017.
 */
interface BaseContract {

    interface ViewModel {

        val error: ObservableField<String>

        fun onResume()

        fun onPause()
    }
}