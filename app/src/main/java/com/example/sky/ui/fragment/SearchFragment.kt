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
import com.example.sky.model.SearchDetails
import com.example.sky.model.SearchResponse
import com.example.sky.network.RestApi
import com.example.sky.ui.adapter.SearchResultAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.HttpException
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject


class SearchFragment : BaseFragment() {

    @Inject lateinit var restApi: RestApi
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
            // TODO save searchDetails in instanceState
            setUpAdapter(searchResultRV, getSearchDetails(), searchResponse!!)
            showLoading(false)
        }
        else{
            pricingGetSession(searchResultRV, getSearchDetails())
        }
    }

    private fun pricingGetSession(searchResultRV: RecyclerView, searchDetails: SearchDetails){
        callDisposable = restApi.pricingGetSession(
                cabinclass = searchDetails.cabinclass,
                country = searchDetails.country,
                currency = searchDetails.currency,
                locale = searchDetails.locale,
                locationSchema = searchDetails.locationSchema,
                originplace = searchDetails.originplace,
                destinationplace = searchDetails.destinationplace,
                outbounddate = searchDetails.outbounddate,
                inbounddate = searchDetails.inbounddate,
                adults = searchDetails.adults
            )
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
                    setUpAdapter(searchResultRV, searchDetails, searchResponse!!)
                    updateHeader(searchResponse!!)
                },
                {t ->
                    when(t){
                        is UnknownHostException -> {
                            showError(getString(R.string.network_error))
                        }
                        is NullPointerException -> {
                            showError(getString(R.string.could_not_load_data))
                        }
                        is HttpException -> {
                            showError(getString(R.string.http_error))
                        }
                        else -> {
                            showError(getString(R.string.error))
                        }
                    }
                    // TODO: retrofit2.adapter.rxjava2.httpexception: http 304 not modified
                    Timber.d("Loading Error")
                    Timber.d(getStackTrace(t))
                }
            )
    }

    // TODO: pass as intent
    public fun getSearchDetails(): SearchDetails{
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

    private fun setUpAdapter(searchResultRV: RecyclerView, searchDetails: SearchDetails, searchResponse: SearchResponse){
        searchResultRV.layoutManager = LinearLayoutManager(activity as Context, LinearLayout.VERTICAL, false)
        searchResultRV.adapter = SearchResultAdapter(searchDetails, searchResponse, activity as Context)
    }
    private fun updateHeader(searchResponse: SearchResponse){
        search_result_count.text = getString(R.string.search_result_count, searchResponse?.Itineraries?.size, searchResponse?.Itineraries?.size)
        sort_and_filters.text = getString(R.string.sort_and_filters)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(SEARCH_RESPONSE_KEY, searchResponse)
    }

    override fun onDestroy() {
        super.onDestroy()
        callDisposable?.dispose()
    }

}
