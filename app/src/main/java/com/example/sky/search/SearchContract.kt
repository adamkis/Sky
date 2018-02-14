package com.example.sky.search

import android.support.annotation.StringRes
import com.example.sky.BasePresenter
import com.example.sky.BaseView
import com.example.sky.model.SearchDetails
import com.example.sky.model.SearchResponse

interface SearchContract {

    interface View : BaseView<Presenter> {

        fun setProgressIndicator(active: Boolean)

        fun showSearchResults(searchResponse: SearchResponse, searchDetails: SearchDetails)

        fun showError(@StringRes stringRes: Int)

    }

    interface Presenter : BasePresenter

}