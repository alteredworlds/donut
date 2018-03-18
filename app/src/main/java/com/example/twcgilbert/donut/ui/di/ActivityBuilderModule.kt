package com.example.twcgilbert.donut.ui.di

import com.example.twcgilbert.donut.common.di.ActivityScope
import com.example.twcgilbert.donut.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by twcgilbert on 01/10/2017.
 */
@Module
abstract class ActivityBuilderModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity
}