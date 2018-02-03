package com.example.sky.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Leg(
        var Id: String? = null,
        var Departure: String? = null,
        var Arrival: String? = null,
        var OriginStation: String? = null,
        var DestinationStation: String? = null,
        var Duration: String? = null,
        var Stops: Array<String>? = null,
        var Carriers: Array<String>? = null
    ) : Parcelable