package com.example.twcgilbert.donut.di

import android.app.Application
import com.example.twcgilbert.donut.CreditDonutTestApplication
import com.example.twcgilbert.donut.repo.di.TestApiModule
import com.example.twcgilbert.donut.ui.di.ActivityBuilderModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by twcgilbert on 11/03/2018.
 */
@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        TestApiModule::class,
        ActivityBuilderModule::class))
interface TestAppComponent {
    fun inject(application: CreditDonutTestApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestAppComponent
    }
}