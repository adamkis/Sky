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
import com.example.sky.App
import com.example.sky.R
import com.example.sky.helper.FilePersistenceHelper
import com.example.sky.helper.getStackTrace
import com.example.sky.helper.logDebug
import com.example.sky.model.SearchDetails
import com.example.sky.model.SearchResponse
import com.example.sky.network.RestApi
import com.example.sky.search.SearchContract
import com.example.sky.ui.adapter.SearchResultAdapter
import io.paperdb.Paper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject


class SearchFragment : BaseFragment(), SearchContract.View {


    lateinit private var mPresenter: SearchContract.Presenter
    lateinit private var searchResultRV: RecyclerView

//    @Inject lateinit var restApi: RestApi
//    private var callDisposable: Disposable? = null

//    private var searchResponse: SearchResponse? = null
//    private var searchDetails: SearchDetails? = null

    companion object {
//        private val SEARCH_RESPONSE_KEY = "SEARCH_RESPONSE_KEY"
//        private val SEARCH_DETAILS_KEY = "SEARCH_DETAILS_KEY"
//        fun newInstance(searchDetails: SearchDetails): SearchFragment {
        fun newInstance(): SearchFragment {
            val fragment = SearchFragment()
//            val args = Bundle()
//            args.putParcelable(SEARCH_DETAILS_KEY, searchDetails)
//            fragment.arguments = args
            return fragment
        }
    }

    override fun setPresenter(presenter: SearchContract.Presenter) {
        // TODO nullcheck
        mPresenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        App.netComponent.inject(this)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLoadingAndError(view.findViewById(R.id.loading), view as CoordinatorLayout)
        searchResultRV = view.findViewById(R.id.search_result_recycler_view)
//        if (arguments != null) {
//            searchDetails = arguments!!.getParcelable(SEARCH_DETAILS_KEY)
//        }
//        if( savedInstanceState != null ){
//            searchResponse = savedInstanceState.getParcelable(SEARCH_RESPONSE_KEY)
//            searchDetails = savedInstanceState.getParcelable(SEARCH_DETAILS_KEY)
//        }
//        if(searchResponse != null){
//            setUpAdapter(searchResultRV, searchDetails!!, searchResponse!!)
//            showLoading(false)
//        }
//        else{
//            downloadData(searchResultRV, searchDetails!!)
//        }
        // TODO remove
//        header.setOnClickListener {
//            downloadData(searchResultRV, searchDetails!!)
//        }
    }


    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }


//    private fun downloadData(searchResultRV: RecyclerView, searchDetails: SearchDetails){
//        callDisposable = restApi.pricingGetSession(
//                cabinclass = searchDetails.cabinclass,
//                country = searchDetails.country,
//                currency = searchDetails.currency,
//                locale = searchDetails.locale,
//                locationSchema = searchDetails.locationSchema,
//                originplace = searchDetails.originplace,
//                destinationplace = searchDetails.destinationplace,
//                outbounddate = searchDetails.outbounddate,
//                inbounddate = searchDetails.inbounddate,
//                adults = searchDetails.adults
//            )
//            .map{
//                response -> response.headers().get("Location")
//            }
//            .flatMap {
//                location -> restApi.pricingPollResults(RestApi.addApiKey(location))
//            }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { showLoading(true) }
//            .doAfterTerminate { showLoading(false) }
//            .subscribe(
//                { searchResponse ->
//                    showResults(searchResponse, searchResultRV, searchDetails)
//                    saveResults(searchResponse)
//                },
//                { t -> handleError(t, searchResultRV, searchDetails) }
//            )
//    }

    override fun showSearchResults(searchResponse: SearchResponse, searchDetails: SearchDetails){
        setUpAdapter(searchResultRV, searchDetails, searchResponse!!)
        updateHeader(searchResponse)
    }

    override fun handleError(t: Throwable, searchDetails: SearchDetails){
        when(t){
            is UnknownHostException -> {
                showError(getString(R.string.network_error))
            }
            is NullPointerException -> {
                showError(getString(R.string.could_not_load_data))
            }
            is HttpException -> {
                handleHttpException(t, searchResultRV, searchDetails)
            }
            else -> {
                showError(getString(R.string.error))
            }
        }
        logDebug(getStackTrace(t))
    }

    private fun handleHttpException(e: HttpException, searchResultRV: RecyclerView, searchDetails: SearchDetails){
        if(e.message?.contains("304") == true){
            loadSavedResults()?.let { showSearchResults(it, searchDetails) }
        }
        else{
            showError(getString(R.string.http_error))
        }
    }

//    private fun saveResults(searchResponse: SearchResponse){
//        Paper.book().write(FilePersistenceHelper.RESPONSE_KEY, searchResponse)
//    }

    private fun loadSavedResults(): SearchResponse{
        return Paper.book().read(FilePersistenceHelper.RESPONSE_KEY)
    }

    private fun setUpAdapter(searchResultRV: RecyclerView, searchDetails: SearchDetails, searchResponse: SearchResponse){
        searchResultRV.layoutManager = LinearLayoutManager(activity as Context, LinearLayout.VERTICAL, false)
        searchResultRV.adapter = SearchResultAdapter(searchDetails, searchResponse, activity as Context)
    }
    private fun updateHeader(searchResponse: SearchResponse){
        search_result_count.text = getString(R.string.search_result_count, searchResponse.Itineraries?.size, searchResponse.Itineraries?.size)
        sort_and_filters.text = getString(R.string.sort_and_filters)
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putParcelable(SEARCH_RESPONSE_KEY, searchResponse)
//        outState.putParcelable(SEARCH_DETAILS_KEY, searchDetails)
//    }

    // TODO handle it in presenter
//    override fun onDestroy() {
//        super.onDestroy()
//        callDisposable?.dispose()
//    }

}
