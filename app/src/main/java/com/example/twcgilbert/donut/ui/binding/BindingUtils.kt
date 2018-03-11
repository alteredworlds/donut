package com.example.twcgilbert.donut.ui.binding

import android.databinding.BindingAdapter
import com.example.twcgilbert.donut.ui.widget.CreditScoreDonut

/**
 * Created by twcgilbert on 01/10/2017.
 */

@BindingAdapter("progress", "max")
fun setProgressMax(creditScoreDonut: CreditScoreDonut, value: Int, max: Int) {
    creditScoreDonut.setProgressAndMax(value, max)
}