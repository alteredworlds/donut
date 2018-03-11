package com.example.twcgilbert.donut.repo.network

import com.example.twcgilbert.donut.repo.data.CreditReport
import io.reactivex.Single
import retrofit2.Response
import java.util.concurrent.TimeUnit

/**
 * Created by twcgilbert on 11/03/2018.
 */
class CreditReportServiceDummyDelayed : CreditReportServiceDummy() {

    val DELAY = 1L

    override fun getCreditReport(): Single<Response<CreditReport>> {
        return super.getCreditReport().delay(DELAY, TimeUnit.SECONDS)
    }
}