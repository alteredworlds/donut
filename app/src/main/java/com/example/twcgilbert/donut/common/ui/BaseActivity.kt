package com.example.twcgilbert.donut.common.ui

import android.widget.Toast
import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by twcgilbert on 01/10/2017.
 */
abstract class BaseActivity : DaggerAppCompatActivity(), BaseContract.View {

    override fun showError(message: String) {
        // we'd replace this with something more in tune with overall app UX
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}