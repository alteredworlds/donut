package com.example.twcgilbert.donut.repo

import com.example.twcgilbert.donut.repo.data.CreditReport
import com.example.twcgilbert.donut.repo.network.CreditReportService
import io.reactivex.Single
import timber.log.Timber

/**
 * Created by twcgilbert on 06/03/2018.
 */
class DataRepositoryImpl(private val api: CreditReportService) : DataRepository {

    override fun getCreditReport(): Single<CreditReport> = api.getCreditReport()
            .doAfterSuccess() {
                // purely for logging, to see if came from cache or network
                // would be removed from production code
                if (it.raw().cacheResponse() != null) {
                    Timber.d("Response came from cache")
                }
                if (it.raw().networkResponse() != null) {
                    Timber.d("Response came from server")
                }
            }
            .map {
                it.body()
            }
}