package com.example.sky.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class SearchResponse(
        var SessionKey: String? = null,
        var Status: String? = null,
        var Itineraries: Array<Itinerary>? = null
    ) : Parcelable