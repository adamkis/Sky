package com.example.sky

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.example.sky.helper.MatcherUtils.atPosition
import com.example.sky.helper.MockResponseStrings
import com.example.sky.network.RestApi
import com.example.sky.search.SearchActivity
import com.squareup.okhttp.mockwebserver.Dispatcher
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import com.squareup.okhttp.mockwebserver.RecordedRequest
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchActivityMockWebServerInstrumentedTest {

    @Suppress("unused") // actually used by Espresso
    @get:Rule
    var mActivityRule: ActivityTestRule<SearchActivity> = ActivityTestRule(SearchActivity::class.java, true, false)
    lateinit private var server: MockWebServer
    val app by lazy { InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as App }

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                if (request.path.contains("/pricing/v1.0")) {
                    return MockResponse().setResponseCode(200).setHeader("Location", "mock-location")
                }
                else if (request.path.contains("mock-location")) {
                    return MockResponse().setResponseCode(200).setBody(MockResponseStrings.MOCK_RESPONSE_PRICING)
                }
                return MockResponse().setResponseCode(404)
            }
        }
        server.setDispatcher(dispatcher)
        app.setNetComponent(app.createNetComponent(server.url("/").toString()))
        mActivityRule.launchActivity(Intent())
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun searchActivity_isDisplayed() {
        onView(withId(R.id.search_result_recycler_view))
                .check(matches(atPosition(0, hasDescendant(withText("LHR-EDI, British Airways")))))
    }

}