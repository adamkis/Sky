package com.example.sky

import android.content.Context
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.example.sky.helper.MatcherUtils.atPosition
import com.example.sky.helper.TestUtils.readRawToString
import com.example.sky.search.SearchActivity
import com.squareup.okhttp.mockwebserver.Dispatcher
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import com.squareup.okhttp.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchActivityMockWebServerInstrumentedTest {

    @Suppress("unused") // actually used by Espresso
    @get:Rule
    private var mActivityRule: ActivityTestRule<SearchActivity> = ActivityTestRule(SearchActivity::class.java, true, false)
    lateinit private var server: MockWebServer
    private val app by lazy { InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as App }

    @Before
    fun setUp() {
        val MOCK_LOCATION = "mock-location"
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        server = MockWebServer()
        server.start()
        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                if (request.path.contains("/pricing/v1.0")) {
                    return MockResponse().setResponseCode(200).setHeader("Location", MOCK_LOCATION)
                }
                else if (request.path.contains(MOCK_LOCATION)) {
                    return MockResponse().setResponseCode(200).setBody(readRawToString(context, R.raw.response_pricing_small))
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