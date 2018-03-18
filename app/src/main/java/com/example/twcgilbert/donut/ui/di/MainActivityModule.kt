package com.example.twcgilbert.donut.ui.di

import com.example.twcgilbert.donut.common.di.ActivityScope
import com.example.twcgilbert.donut.repo.DataRepository
import com.example.twcgilbert.donut.ui.MainActivityContract
import com.example.twcgilbert.donut.ui.MainActivityViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by twcgilbert on 06/03/2018.
 */
@Module
class MainActivityModule {
    @Provides
    @ActivityScope
    fun providesViewModel(
            repository: DataRepository): MainActivityContract.ViewModel =
            MainActivityViewModel(repository)
}