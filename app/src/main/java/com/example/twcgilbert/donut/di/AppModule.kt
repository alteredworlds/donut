package com.example.twcgilbert.donut.di

import android.app.Application
import com.example.twcgilbert.donut.DonutApplication
import dagger.Binds
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by twcgilbert on 07/03/2018.
 */
@Module(includes = arrayOf(AndroidSupportInjectionModule::class))
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun providesApplication(app: DonutApplication): Application
}