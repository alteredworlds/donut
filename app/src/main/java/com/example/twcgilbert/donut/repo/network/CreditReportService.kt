package com.example.twcgilbert.donut.repo.network

import com.example.twcgilbert.donut.repo.data.CreditReport
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by twcgilbert on 06/03/2018.
 */
interface CreditReportService {
    @GET("mockcredit/values")
    fun getCreditReport(): Single<Response<CreditReport>>
}