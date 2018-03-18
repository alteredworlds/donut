package com.example.twcgilbert.donut.ui.binding

import android.databinding.BindingAdapter
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import com.example.twcgilbert.donut.ui.widget.CreditScoreDonut

/**
 * Created by twcgilbert on 01/10/2017.
 */

@BindingAdapter("progress", "max")
fun setProgressMax(creditScoreDonut: CreditScoreDonut, value: Int, max: Int) {
    creditScoreDonut.setProgressAndMax(value, max)
}

@BindingAdapter("snackBarText")
fun setSnackbarText(coordinatorLayout: CoordinatorLayout, text: String?) {
    if ((null != text) && text.isNotEmpty()) {
        Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_LONG)
                .show();
    }
}