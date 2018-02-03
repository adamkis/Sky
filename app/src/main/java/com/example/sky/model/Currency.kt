package com.example.sky.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Currency(
        var Code: String? = null,
        var Symbol: String? = null
    ) : Parcelable