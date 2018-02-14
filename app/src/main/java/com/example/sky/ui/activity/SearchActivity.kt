package com.example.sky.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
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

//    private var activeFragment: Fragment? = null
//    private val ACTIVE_FRAGMENT_KEY = "ACTIVE_FRAGMENT_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val searchDetails = getMockSearchDetails()
        search_title_places.text = getString(R.string.search_title_places, searchDetails.originplace, searchDetails.destinationplace)
        search_title_details.text = formatSearchDetails(searchDetails, this)

//        replaceFragment(
//            if(savedInstanceState != null) supportFragmentManager.getFragment(savedInstanceState, ACTIVE_FRAGMENT_KEY)
//            else SearchFragment.newInstance()
//        )


        var searchFragment: SearchFragment? = supportFragmentManager
                .findFragmentById(R.id.fragment_container) as? SearchFragment
        if (searchFragment == null) {
            // TODO change it to putting in bundle
            searchFragment = SearchFragment.newInstance()
            // TODO put this back
//            ActivityUtils.addFragmentToActivity(supportFragmentManager,
//                    searchFragment, R.id.contentFrame)
            // TODO nuccheck on id and fragment
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_container, searchFragment)
            transaction.commit()
        }

        SearchPresenter(searchFragment)
    }

//    override fun onSaveInstanceState(outState: Bundle?) {
//        super.onSaveInstanceState(outState)
//        supportFragmentManager.putFragment(outState, ACTIVE_FRAGMENT_KEY, activeFragment);
//    }

//    private fun replaceFragment(fragment: Fragment){
//        activeFragment = fragment
//        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, activeFragment).commit()
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // TODO remove and only have it in presenter
    fun getMockSearchDetails(): SearchDetails {
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

    // TODO: handling the 304
    // TODO: testing


}
