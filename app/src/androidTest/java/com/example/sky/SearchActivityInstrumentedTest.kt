package com.example.sky

import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.example.sky.helper.TestUtils
import com.example.sky.search.SearchActivity
import org.junit.Rule
import android.support.test.espresso.matcher.ViewMatchers.withParent
import android.widget.TextView
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf


@RunWith(AndroidJUnit4::class)
class SearchActivityInstrumentedTest {

    @Suppress("unused") // actually used by Espresso
    @get:Rule
    val activityRule = ActivityTestRule<SearchActivity>(SearchActivity::class.java)

    @Test
    fun toolbarShouldBeShown() {
        onView(withText("Edinburgh - London")).check(matches(ViewMatchers.isDisplayed()))
        onView(withText("19 Feb - 20 Feb, 1 adult, Economy")).check(matches(ViewMatchers.isDisplayed()))
    }

}
