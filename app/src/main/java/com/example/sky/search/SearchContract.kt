package com.example.sky.search

import com.example.sky.BasePresenter
import com.example.sky.BaseView
import com.example.sky.model.SearchDetails
import com.example.sky.model.SearchResponse

interface SearchContract {

    interface View : BaseView<Presenter> {

//        val isActive: Boolean
//
//        fun showStatistics(numberOfIncompleteTasks: Int, numberOfCompletedTasks: Int)
//
//        fun showLoadingStatisticsError()

        fun setProgressIndicator(active: Boolean)

        fun showSearchResults(searchResponse: SearchResponse, searchDetails: SearchDetails)

        fun handleError(t: Throwable, searchDetails: SearchDetails)
    }

    interface Presenter : BasePresenter
}