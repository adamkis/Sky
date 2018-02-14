package com.example.sky

import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.example.sky.helper.TestUtils
import com.example.sky.ui.activity.SearchActivity
import org.junit.Rule
import android.support.test.espresso.matcher.ViewMatchers.withParent
import android.widget.TextView
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf


@RunWith(AndroidJUnit4::class)
class SearchActivityInstrumentedTest {

    @Suppress("unused") // actually used by Espresso
    @get:Rule
    val activityRule = ActivityTestRule<SearchActivity>(SearchActivity::class.java)

    @Test
    fun homeActivity_Displayed() {
        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.action_bar)))).check(matches(withText(TestUtils.getString(R.string.title_home))))
    }

    @Test
    fun homeActivity_BottomNavigationClicks(){
        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.action_bar)))).check(matches(withText(TestUtils.getString(R.string.title_home))))
        onView(withId(R.id.navigation_search)).perform(click())
        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.action_bar)))).check(matches(withText(TestUtils.getString(R.string.title_search))))
        onView(withId(R.id.navigation_home)).perform(click())
        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.action_bar)))).check(matches(withText(TestUtils.getString(R.string.title_home))))
    }

}
