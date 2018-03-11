package com.example.twcgilbert.donut.repo.network

import com.example.twcgilbert.donut.repo.data.CreditReport
import com.example.twcgilbert.donut.repo.data.CreditReportInfo
import io.reactivex.Single
import retrofit2.Response

/**
 * Created by twcgilbert on 11/03/2018.
 */
open class CreditReportServiceDummy : CreditReportService {

    companion object {
        const val SCORE = 123
        const val MAX_SCORE = 456
        const val MIN_SCORE = 0
    }

    override fun getCreditReport(): Single<Response<CreditReport>> {
        return Single.just(Response.success(CreditReport(CreditReportInfo(
                SCORE,
                MAX_SCORE,
                MIN_SCORE
        ))))
    }
}