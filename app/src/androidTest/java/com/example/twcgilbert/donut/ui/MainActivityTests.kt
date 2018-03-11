package com.example.twcgilbert.donut.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.twcgilbert.donut.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by twcgilbert on 11/03/2018.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTests {

    @get:Rule
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun testOpenView() {
        onView(withId(R.id.toolbar_title)).check(matches(withText(R.string.dashboard)))
    }
}