package com.example.twcgilbert.donut.common.ui

/**
 * Created by twcgilbert on 23/11/2017.
 */
interface BaseContract {

    interface View {

        fun showError(message: String)
    }

    interface ViewModel {

        fun onResume()

        fun onPause()
    }
}