package com.example.twcgilbert.donut

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner

/**
 * Created by twcgilbert on 11/03/2018.
 */
class CreditDonutTestRunner : AndroidJUnitRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, CreditDonutTestApplication::class.java.name, context)
    }
}