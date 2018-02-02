package com.example.sky.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.sky.R
import com.example.sky.ui.fragment.SearchFragment


class MainActivity : AppCompatActivity() {

    private var activeFragment: Fragment? = null
    private val ACTIVE_FRAGMENT_KEY = "ACTIVE_FRAGMENT_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(
            if(savedInstanceState != null) supportFragmentManager.getFragment(savedInstanceState, ACTIVE_FRAGMENT_KEY)
            else SearchFragment.newInstance()
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

}
