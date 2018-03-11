package com.example.twcgilbert.donut.ui

import com.example.twcgilbert.donut.repo.DataRepositoryImpl
import com.example.twcgilbert.donut.repo.network.CreditReportServiceDummy
import com.example.twcgilbert.donut.repo.network.CreditReportServiceDummyDelayed
import com.example.twcgilbert.donut.repo.network.CreditReportServiceNoNetwork
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

//import org.mockito.Mock

/**
 * Created by twcgilbert on 11/03/2018.
 */
class MainActivityViewModelTests : CreditDonutTestBase() {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var view: MainActivityContract.View

    private lateinit var viewModel: MainActivityContract.ViewModel

    override fun cleanup() {
        viewModel.onPause()
    }

    @Test
    fun testViewModel() {

        viewModel = MainActivityViewModel(
                view,
                DataRepositoryImpl(CreditReportServiceDummy()))

        expectNotInProgressAllZero()
    }

    @Test
    fun testViewModelNoNetwork() {

        viewModel = MainActivityViewModel(
                view,
                DataRepositoryImpl(CreditReportServiceNoNetwork()))

        expectNotInProgressAllZero()

        viewModel.onResume()

        // advance time by tiny increment...
        testScheduler.advanceTimeBy(1, TimeUnit.MICROSECONDS)

        // we expect no network state to have been notified to the view
        verify(view).showError(CreditReportServiceNoNetwork.NO_NETWORK)
    }

    @Test
    fun testViewModelRetrieveData() {
        viewModel = MainActivityViewModel(
                view,
                DataRepositoryImpl(CreditReportServiceDummy()))

        expectNotInProgressAllZero()

        // we can wait as long as we want, no data since haven't subscribed
        testScheduler.advanceTimeTo(2, TimeUnit.SECONDS)

        expectNotInProgressAllZero()

        // subscribe to the repo
        viewModel.onResume()

        // in progress but still no items, as time hasn't advanced since subscribe(...)
        expectInProgressAllZero()

        // OK, advance time by tiny increment...
        testScheduler.advanceTimeBy(1, TimeUnit.MICROSECONDS)

        // with no delay, we now expect results
        expectNotInProgressSuccess()
    }

    @Test
    fun testViewModelRetrieveDataDelayed() {
        viewModel = MainActivityViewModel(
                view,
                DataRepositoryImpl(CreditReportServiceDummyDelayed()))

        expectNotInProgressAllZero()

        // we can wait as long as we want, no data since haven't subscribed
        testScheduler.advanceTimeTo(2, TimeUnit.SECONDS)

        expectNotInProgressAllZero()

        // OK, set time to 0
        testScheduler.advanceTimeTo(0, TimeUnit.MICROSECONDS)

        // subscribe to the repo
        viewModel.onResume()

        // in progress but still no items, as time hasn't advanced since subscribe(...)
        expectInProgressAllZero()

        // OK, set time to 1 ms...
        testScheduler.advanceTimeTo(1, TimeUnit.MICROSECONDS)

        // in progress but still no items, as we haven't advanced enough
        expectInProgressAllZero()

        // OK, set time to 2 s...
        testScheduler.advanceTimeTo(2, TimeUnit.SECONDS)

        // we now expect results
        expectNotInProgressSuccess()
    }

    private fun expectNotInProgressAllZero() {
        assertEquals(0, viewModel.maxScore.get())
        assertEquals(0, viewModel.score.get())
        assertEquals(false, viewModel.inProgress.get())
    }

    private fun expectInProgressAllZero() {
        assertEquals(0, viewModel.maxScore.get())
        assertEquals(0, viewModel.score.get())
        assertEquals(true, viewModel.inProgress.get())
    }

    private fun expectNotInProgressSuccess() {
        assertEquals(CreditReportServiceDummy.MAX_SCORE, viewModel.maxScore.get())
        assertEquals(CreditReportServiceDummy.SCORE, viewModel.score.get())
        assertEquals(false, viewModel.inProgress.get())
    }
}