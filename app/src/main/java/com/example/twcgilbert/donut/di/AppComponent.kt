package com.example.twcgilbert.donut.di

import android.app.Application
import com.example.twcgilbert.donut.DonutApplication
import com.example.twcgilbert.donut.repo.di.ApiModule
import com.example.twcgilbert.donut.ui.di.ActivityBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import dagger.android.AndroidInjector



/**
 * Created by twcgilbert on 01/10/2017.
 */
@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        ApiModule::class,
        ActivityBuilderModule::class))
internal interface AppComponent : AndroidInjector<DonutApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<DonutApplication>()
}