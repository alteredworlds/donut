package com.example.twcgilbert.donut.repo

import com.example.twcgilbert.donut.repo.data.CreditReport
import io.reactivex.Single

/**
 * Created by twcgilbert on 06/03/2018.
 */
interface DataRepository {

    fun getCreditReport(): Single<CreditReport>
}