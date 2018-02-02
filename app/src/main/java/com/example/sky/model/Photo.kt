package com.example.sky.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class Photo(var isfamily: String? = null, var farm: String? = null, var id: String? = null, var title: String? = null,
                 var ispublic: String? = null, var owner: String? = null, var secret: String? = null, var server: String? = null, var isfriend: String? = null)
    : Parcelable{

    companion object {
        const val TAG = "Photo"
    }

    fun getUrl(): String {
        // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
        return StringBuilder()
                .append("https://farm")
                .append(farm)
                .append(".staticflickr.com/")
                .append(server)
                .append("/")
                .append(id)
                .append("_")
                .append(secret)
                .append(".jpg")
                .toString()
    }
}
