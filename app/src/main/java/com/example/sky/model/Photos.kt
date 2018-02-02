package com.example.sky.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class Photos(var total: String? = null, var page: String? = null, var pages: String? = null, var photo: Array<Photo>? = null, var perpage: String? = null) : Parcelable