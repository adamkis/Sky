package com.example.sky.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Carrier(
        var Id: String? = null,
        var Code: String? = null,
        var Name: String? = null
    ) : Parcelable