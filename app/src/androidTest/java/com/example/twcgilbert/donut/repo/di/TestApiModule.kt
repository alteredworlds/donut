package com.example.twcgilbert.donut.repo.di

import com.example.twcgilbert.donut.repo.DataRepository
import com.example.twcgilbert.donut.repo.DataRepositoryImpl
import com.example.twcgilbert.donut.repo.network.CreditReportServiceDummy
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by twcgilbert on 11/03/2018.
 */

@Module
class TestApiModule {

    @Provides
    @Singleton
    fun provideDataRepository(): DataRepository {
        return DataRepositoryImpl(CreditReportServiceDummy())
    }
}