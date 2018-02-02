package com.example.sky.network

import com.example.sky.model.PhotosResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface RestApi {

    @GET("?method=flickr.photos.getRecent")
    fun getRecentPhotos(): Observable<PhotosResponse>

}