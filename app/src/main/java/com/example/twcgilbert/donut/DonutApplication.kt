package com.example.twcgilbert.donut

import android.app.Activity
import android.app.Application
import com.example.twcgilbert.donut.di.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by twcgilbert on 06/03/2018.
 */
open class DonutApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        // memory leak detection library (NOP'd out of release builds)
        LeakCanary.install(this);
        injectDependencies()
        setupLogger()
    }

    protected open fun injectDependencies() {
        DaggerAppComponent
                .builder()
                .create(this)
                .inject(this)
    }

    private fun setupLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun activityInjector() = dispatchingActivityInjector
}