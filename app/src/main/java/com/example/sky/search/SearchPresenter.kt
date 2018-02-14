package com.example.sky.search

import com.example.sky.App
import com.example.sky.R
import com.example.sky.helper.FilePersistenceHelper
import com.example.sky.helper.getStackTrace
import com.example.sky.helper.logDebug
import com.example.sky.helper.logThrowable
import com.example.sky.model.SearchDetails
import com.example.sky.model.SearchResponse
import com.example.sky.network.RestApi
import io.paperdb.Paper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class SearchPresenter(private val mSearchView: SearchContract.View, private val searchDetails: SearchDetails) : SearchContract.Presenter {

    @Inject lateinit var restApi: RestApi
    private var callDisposable: Disposable? = null
    private var paperDisposable: Disposable? = null

    init {
        App.netComponent.inject(this)
        mSearchView.setPresenter(this)
    }

    override fun start() {
        loadSearchResults(searchDetails)
    }

    private fun loadSearchResults(searchDetails: SearchDetails){
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
            .doOnSubscribe { mSearchView.setProgressIndicator(true) }
            .doAfterTerminate { mSearchView.setProgressIndicator(false) }
            .subscribe(
                { searchResponse ->
                    mSearchView.showSearchResults(searchResponse, searchDetails)
                    saveResults(searchResponse)
                },
                { t -> handleError(t, searchDetails) }
            )
    }


    private fun handleError(t: Throwable, searchDetails: SearchDetails){
        when(t){
            is UnknownHostException -> {
                mSearchView.showError(R.string.network_error)
            }
            is NullPointerException -> {
                mSearchView.showError(R.string.could_not_load_data)
            }
            is HttpException -> {
                handleHttpException(t, searchDetails)
            }
            else -> {
                mSearchView.showError(R.string.error)
            }
        }
        logThrowable(t)
    }

    private fun handleHttpException(e: HttpException, searchDetails: SearchDetails){
        paperDisposable =  Observable.just<SearchResponse>( loadSavedResults() )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { searchResponse ->
                    if(e.message?.contains("304") == true && searchResponse != null){
                        mSearchView.showSearchResults(searchResponse, searchDetails)
                    }
                    else{
                        mSearchView.showError(R.string.http_error)
                    }
                },
                { logThrowable(it) }
            )
    }

    private fun saveResults(searchResponse: SearchResponse){
        paperDisposable =  Observable.just(
                Paper.book().write(FilePersistenceHelper.RESPONSE_KEY, searchResponse) )
            .subscribeOn(Schedulers.io())
            .subscribe (
                { _ -> },
                { logThrowable(it) }
            )
    }

    private fun loadSavedResults(): SearchResponse?{
        return Paper.book().read(FilePersistenceHelper.RESPONSE_KEY, null)
    }

    override fun onViewAttached() {}

    override fun onViewDetached() {
        callDisposable?.dispose()
        paperDisposable?.dispose()
    }

}