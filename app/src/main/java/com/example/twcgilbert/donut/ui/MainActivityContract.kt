package com.example.twcgilbert.donut.ui

import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import com.example.twcgilbert.donut.common.ui.BaseContract

/**
 * Created by twcgilbert on 06/03/2018.
 */
interface MainActivityContract {

    interface ViewModel : BaseContract.ViewModel {

        val maxScore: ObservableInt

        val score: ObservableInt

        val inProgress: ObservableBoolean
    }
}