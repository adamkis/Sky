package com.example.sky.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Itinerary(var OutboundLegId: String? = null, var InboundLegId: String? = null) : Parcelable
