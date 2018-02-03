package com.example.sky.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.example.sky.R
import com.example.sky.model.SearchDetails
import com.example.sky.ui.fragment.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var activeFragment: Fragment? = null
    private val ACTIVE_FRAGMENT_KEY = "ACTIVE_FRAGMENT_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        
        var searchDetails = getSearchDetails()
        origin_and_destination.text = getString(R.string.search_title_places, searchDetails.originplace, searchDetails.destinationplace)

        replaceFragment(
            if(savedInstanceState != null) supportFragmentManager.getFragment(savedInstanceState, ACTIVE_FRAGMENT_KEY)
            else SearchFragment.newInstance(getSearchDetails())
        )
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, ACTIVE_FRAGMENT_KEY, activeFragment);
    }

    private fun replaceFragment(fragment: Fragment){
        activeFragment = fragment
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, activeFragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    public fun getSearchDetails(): SearchDetails {
        return SearchDetails(
                cabinclass = "Economy",
                country = "uk",
                currency = "GBP",
                locale = "en-GB",
                locationSchema = "iata",
                originplace = "EDI",
                destinationplace = "LHR",
                outbounddate = "2018-05-30",
                inbounddate = "2018-06-02",
                adults = "1"
        )
    }

}
