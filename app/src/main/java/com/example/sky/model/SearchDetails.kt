package com.example.sky.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class SearchDetails(
        val cabinclass: String,
        val country: String,
        val currency: String,
        val locale: String,
        val locationSchema: String,
        val originplace: String,
        val destinationplace: String ,
        val outbounddate: String,
        val inbounddate: String,
        val adults: String
    ) : Parcelable