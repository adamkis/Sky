package com.example.sky.search

import android.support.v7.widget.RecyclerView
import com.example.sky.App
import com.example.sky.helper.FilePersistenceHelper
import com.example.sky.helper.getNextMondayAndNextDayReturn
import com.example.sky.model.SearchDetails
import com.example.sky.model.SearchResponse
import com.example.sky.network.RestApi
import io.paperdb.Paper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchPresenter(private val mSearchView: SearchContract.View) : SearchContract.Presenter {

//    private val mSearchView: SearchContract.View


    @Inject lateinit var restApi: RestApi
    private var callDisposable: Disposable? = null

    init {
        // TODO null check
//        mSearchView = checkNotNull(statisticsView, "StatisticsView cannot be null!")
        App.netComponent.inject(this)
        mSearchView.setPresenter(this)
    }

    override fun start() {
        downloadData(getMockSearchDetails())
    }

    private fun downloadData(searchDetails: SearchDetails){
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
                // TODO put back loading
//                .doOnSubscribe { showLoading(true) }
//                .doAfterTerminate { showLoading(false) }
                .subscribe(
                        { searchResponse ->
                            mSearchView.showSearchResults(searchResponse, searchDetails)
                            saveResults(searchResponse)
                        },
                        { t -> mSearchView.handleError(t, searchDetails) }
                )
    }


    private fun saveResults(searchResponse: SearchResponse){
        Paper.book().write(FilePersistenceHelper.RESPONSE_KEY, searchResponse)
    }


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

}