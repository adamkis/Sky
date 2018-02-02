package com.example.sky.network

import com.example.sky.model.PhotosResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface RestApi {

    @FormUrlEncoded
    @POST("pricing/v1.0")
    @Headers(
        "Content-Type: application/x-www-form-urlencoded",
        "Accept: application/json"
    )
    fun getSession( @Field("cabinclass") cabinclass: String = "Economy",
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
            ): Observable<PhotosResponse>

}