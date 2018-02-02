package com.example.sky.network

import android.net.Uri
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*


interface RestApi {

    companion object {

        val SKY_URL_BASE = "http://partners.api.skyscanner.net/apiservices/"
        val SKY_API_KEY: String = "ss630745725358065467897349852985"

        fun addApiKey(@Url urlString: String): String{
            val builtUri: Uri = Uri.parse(urlString)
                    .buildUpon()
                    .appendQueryParameter("apiKey", SKY_API_KEY)
                    .build()
            return builtUri.toString()
        }

    }

    // TODO: Add IP in header
    @FormUrlEncoded
    @POST("pricing/v1.0")
    @Headers(
        "Content-Type: application/x-www-form-urlencoded",
        "Accept: application/json"
    )
    fun pricingGetSession(@Field("cabinclass") cabinclass: String = "Economy",
                          @Field("country") country: String = "uk",
                          @Field("currency") currency: String = "GBP",
                          @Field("locale") locale: String = "en-GB",
                          @Field("locationSchema") locationSchema: String = "iata",
                          @Field("originplace") originplace: String = "EDI",
                          @Field("destinationplace") destinationplace: String = "LHR",
                          @Field("outbounddate") outbounddate: String = "2018-05-30",
                          @Field("inbounddate") inbounddate: String = "2018-06-02",
                          @Field("adults") adults: String = "1",
                          @Field("children") children: String = "0",
                          @Field("infants") infants: String = "0",
                          @Field("apikey") apikey: String = RestApi.SKY_API_KEY
            ): Observable<retrofit2.Response<ResponseBody>>


    @GET
    fun pricingPollResults(@Url url: String): Observable<ResponseBody>

}