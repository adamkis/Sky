package com.example.sky.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.sky.R
import com.example.sky.model.SearchDetails
import com.example.sky.model.SearchResponse
import com.example.sky.search.SearchContract
import com.example.sky.ui.adapter.SearchResultAdapter
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : BaseFragment(), SearchContract.View {


    lateinit private var mPresenter: SearchContract.Presenter
    lateinit private var searchResultRV: RecyclerView

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun setPresenter(presenter: SearchContract.Presenter) {
        mPresenter = checkNotNull(presenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLoadingAndError(view.findViewById(R.id.loading), view as CoordinatorLayout)
        searchResultRV = view.findViewById(R.id.search_result_recycler_view)
        // Starting the presenter
        mPresenter.start()
        // TODO remove
        header.setOnClickListener {
            mPresenter.start()
        }
    }

    override fun showSearchResults(searchResponse: SearchResponse, searchDetails: SearchDetails){
        setUpAdapter(searchResultRV, searchDetails, searchResponse)
        updateHeader(searchResponse)
    }

    private fun setUpAdapter(searchResultRV: RecyclerView, searchDetails: SearchDetails, searchResponse: SearchResponse){
        searchResultRV.layoutManager = LinearLayoutManager(activity as Context, LinearLayout.VERTICAL, false)
        searchResultRV.adapter = SearchResultAdapter(searchDetails, searchResponse, activity as Context)
    }
    private fun updateHeader(searchResponse: SearchResponse){
        search_result_count.text = getString(R.string.search_result_count, searchResponse.Itineraries?.size, searchResponse.Itineraries?.size)
        sort_and_filters.text = getString(R.string.sort_and_filters)
    }

    override fun setProgressIndicator(active: Boolean) {
        showLoading(active)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onViewDetached()
    }

}
