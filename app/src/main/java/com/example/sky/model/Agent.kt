package com.example.sky.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Agent(
        var Id: String? = null,
        var Name: String? = null
    ) : Parcelable