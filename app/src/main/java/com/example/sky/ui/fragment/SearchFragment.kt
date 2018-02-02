package com.example.sky.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.sky.App
import com.example.sky.R
import com.example.sky.helper.getStackTrace
import com.example.sky.model.SearchResponse
import com.example.sky.network.RestApi
import com.example.sky.ui.adapter.SearchResultAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject


class SearchFragment : BaseFragment() {

    @Inject lateinit var restApi: RestApi
    private var clickDisposable: Disposable? = null
    private var callDisposable: Disposable? = null
    private var searchResponse: SearchResponse? = null
    private val SEARCH_RESPONSE_KEY = "SEARCH_RESPONSE_KEY"

    companion object {
        fun newInstance(): SearchFragment {
            val fragment = SearchFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        App.netComponent.inject(this)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLoadingAndError(view.findViewById(R.id.loading), view as CoordinatorLayout)
        val searchResultRV: RecyclerView = view.findViewById(R.id.search_result_recycler_view)
        searchResponse = savedInstanceState?.getParcelable(SEARCH_RESPONSE_KEY)
        if(searchResponse != null){
            setUpAdapter(searchResultRV, searchResponse!!)
            showLoading(false)
        }
        else{
            pricingGetSession(searchResultRV)
        }
    }

    private fun pricingGetSession(searchResultRV: RecyclerView){
        callDisposable = restApi.pricingGetSession()
            .map{
                response -> response.headers().get("Location")
            }
            .flatMap {
                location -> restApi.pricingPollResults(RestApi.addApiKey(location))
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading(true) }
            .doAfterTerminate { showLoading(false) }
            .subscribe(
                { searchResponse ->
                    Timber.d("SkyResponse_Subscribe: " + searchResponse.toString())
                    this@SearchFragment.searchResponse = searchResponse
                    setUpAdapter(searchResultRV, searchResponse!!)
                },
                {t ->
                    when(t){
                        is UnknownHostException -> {
                            showError(getString(R.string.network_error))
                        }
                        is NullPointerException -> {
                            showError(getString(R.string.could_not_load_data))
                        }
                        else -> {
                            showError(getString(R.string.error))
                        }
                    }
                    Timber.d(getStackTrace(t))
                }
            )
    }

    private fun setUpAdapter(searchResultRV: RecyclerView, searchResponse: SearchResponse){
        searchResultRV.layoutManager = LinearLayoutManager(this@SearchFragment.activity, LinearLayout.VERTICAL, false)
        searchResultRV.adapter = SearchResultAdapter(searchResponse.Itineraries!!, activity as Context)
        clickDisposable = (searchResultRV.adapter as SearchResultAdapter).clickEvent
                .subscribe({
                    startDetailActivityWithTransition(activity as Activity, it.second.findViewById(R.id.carrier_image), it.second.findViewById(R.id.recents_photo_id), it.first)
                })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(SEARCH_RESPONSE_KEY, searchResponse)
    }

    override fun onDestroy() {
        super.onDestroy()
        clickDisposable?.dispose()
        callDisposable?.dispose()
    }

}
