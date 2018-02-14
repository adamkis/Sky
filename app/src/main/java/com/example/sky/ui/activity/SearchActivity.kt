package com.example.sky.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.example.sky.R
import com.example.sky.helper.formatSearchDetails
import com.example.sky.helper.getNextMondayAndNextDayReturn
import com.example.sky.model.SearchDetails
import com.example.sky.search.SearchPresenter
import com.example.sky.ui.fragment.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*


class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val searchDetails = getSearchDetails()
        // Setting up Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        search_title_places.text = getString(R.string.search_title_places, searchDetails.originplace, searchDetails.destinationplace)
        search_title_details.text = formatSearchDetails(searchDetails, this)
        // Creating SearchFragment
        var searchFragment: SearchFragment? = supportFragmentManager
                .findFragmentById(R.id.fragment_container) as? SearchFragment
        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, searchFragment).commit()
        }
        // Creating the Presenter
        SearchPresenter(searchFragment, searchDetails)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun getSearchDetails(): SearchDetails {
        val nextMondayAndNextDayReturn = getNextMondayAndNextDayReturn()
        return SearchDetails(
                cabinclass = "Economy",
                country = "uk",
                currency = "GBP",
                locale = "en-GB",
                locationSchema = "iata",
                originplace = "EDI",
                destinationplace = "LHR",
                outbounddate = nextMondayAndNextDayReturn.first,
                inbounddate = nextMondayAndNextDayReturn.second,
                adults = "1"
        )
    }

}
