package com.example.twcgilbert.donut

import com.example.twcgilbert.donut.di.DaggerTestAppComponent

/**
 * Created by twcgilbert on 11/03/2018.
 */
class CreditDonutTestApplication : DonutApplication() {

    override fun injectDependencies() {
        // can be used to supply alternate modules for testing eg: DataRepository
        DaggerTestAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }
}