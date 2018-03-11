package com.example.twcgilbert.donut.repo.network

import com.example.twcgilbert.donut.repo.data.CreditReport
import io.reactivex.Single
import retrofit2.Response

/**
 * Created by twcgilbert on 11/03/2018.
 */
class CreditReportServiceNoNetwork : CreditReportService {

    companion object {
        const val NO_NETWORK = "no network"
    }

    override fun getCreditReport(): Single<Response<CreditReport>> {
        return Single.error<Response<CreditReport>>(NoNetworkException(NO_NETWORK))
    }
}