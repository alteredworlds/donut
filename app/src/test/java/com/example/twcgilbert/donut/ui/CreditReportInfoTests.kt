package com.example.twcgilbert.donut.ui

import com.example.twcgilbert.donut.repo.data.CreditReport
import com.google.gson.Gson
import org.junit.Test
import timber.log.Timber
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * Created by twcgilbert on 19/03/2018.
 */
class CreditReportInfoTests : CreditDonutTestBase() {

    @Test
    fun testSerlializeCreditReport() {
        val gson = Gson()
        val str = "{}"
        val creditReport = gson.fromJson(str, CreditReport::class.java)
        assertNull(creditReport.creditReportInfo, "deserilize null CreditReportInfo")
    }

    @Test(expected = NullPointerException::class)
    fun testExceptionOnAccess() {
        val gson = Gson()
        val str = "{}"
        val creditReport = gson.fromJson(str, CreditReport::class.java)
        // try and use creditReportInfo in Kotlin
        Timber.d("creditReport.creditReportInfo.score is %d", creditReport.creditReportInfo!!.score);
    }

    @Test
    fun testSerlializeCreditReportInfo() {
        val gson = Gson()
        val str = "{\"creditReportInfo\":{\"score\":514,\"maxScoreValue\":700,\"minScoreValue\":0}}"
        val creditReport = gson.fromJson(str, CreditReport::class.java)
        assertEquals(0, creditReport.creditReportInfo?.minScoreValue)
        assertEquals(700, creditReport.creditReportInfo?.maxScoreValue)
        assertEquals(514, creditReport.creditReportInfo?.score)
    }

    @Test
    fun testSerlializeEmptyCreditReportInfo() {
        val gson = Gson()
        val str = "{\"creditReportInfo\":{}}"
        val creditReport = gson.fromJson(str, CreditReport::class.java)
        assertEquals(0, creditReport.creditReportInfo?.minScoreValue)
        assertEquals(0, creditReport.creditReportInfo?.maxScoreValue)
        assertEquals(0, creditReport.creditReportInfo?.score)
    }
}