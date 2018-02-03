package com.example.sky.network

import android.net.Uri
import com.example.sky.model.SearchDetails
import com.example.sky.model.SearchResponse
import io.reactivex.Observable
import retrofit2.http.*


interface RestApi {

    companion object {

        val SKY_URL_BASE = "http://partners.api.skyscanner.net/apiservices/"
        val SKY_API_KEY: String = "ss630745725358065467897349852985"

        fun addApiKey(@Url urlString: String): String{
            val builtUri: Uri = Uri.parse(urlString)
                    .buildUpon()
                    .appendQueryParameter("apiKey", SKY_API_KEY)
                    // TODO: Put it into a place where it fits better
                    .appendQueryParameter("stops", "0")
                    .build()
            return builtUri.toString()
        }

    }

    // TODO: Add IP in header
    // TODO get always next Monday
    @FormUrlEncoded
    @POST("pricing/v1.0")
    @Headers(
        "Content-Type: application/x-www-form-urlencoded",
        "Accept: application/json"
    )
    fun pricingGetSession(@Field("cabinclass") cabinclass: String = "Economy",
                          @Field("country") country: String,
                          @Field("currency") currency: String,
                          @Field("locale") locale: String,
                          @Field("locationSchema") locationSchema: String,
                          @Field("originplace") originplace: String,
                          @Field("destinationplace") destinationplace: String,
                          @Field("outbounddate") outbounddate: String,
                          @Field("inbounddate") inbounddate: String,
                          @Field("adults") adults: String = "1",
                          @Field("apikey") apikey: String = RestApi.SKY_API_KEY
            ): Observable<retrofit2.Response<Void>>


    @GET
    fun pricingPollResults(@Url url: String): Observable<SearchResponse>

}