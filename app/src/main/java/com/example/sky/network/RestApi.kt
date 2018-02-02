package com.example.sky.network

import com.example.sky.model.PhotosResponse
import io.reactivex.Observable
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RestApi {

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
                          @Field("apikey") apikey: String = "ss630745725358065467897349852985"
            ): Observable<ResponseBody>

    @FormUrlEncoded
    @POST("pricing/v1.0")
    @Headers(
        "Content-Type: application/x-www-form-urlencoded",
        "Accept: application/json"
    )
    fun pricingGetSession2(@Field("cabinclass") cabinclass: String = "Economy",
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
                          @Field("apikey") apikey: String = "ss630745725358065467897349852985"
            ): Call<ResponseBody>


    @GET("pricing/v1.0")
    fun pricingPollResults(@Url url: String): Observable<PhotosResponse>


}