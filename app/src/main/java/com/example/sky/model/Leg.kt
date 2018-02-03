package com.example.sky.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

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
    ) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Leg

        if (Id != other.Id) return false
        if (Departure != other.Departure) return false
        if (Arrival != other.Arrival) return false
        if (OriginStation != other.OriginStation) return false
        if (DestinationStation != other.DestinationStation) return false
        if (Duration != other.Duration) return false
        if (!Arrays.equals(Stops, other.Stops)) return false
        if (!Arrays.equals(Carriers, other.Carriers)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Id?.hashCode() ?: 0
        result = 31 * result + (Departure?.hashCode() ?: 0)
        result = 31 * result + (Arrival?.hashCode() ?: 0)
        result = 31 * result + (OriginStation?.hashCode() ?: 0)
        result = 31 * result + (DestinationStation?.hashCode() ?: 0)
        result = 31 * result + (Duration?.hashCode() ?: 0)
        result = 31 * result + (Stops?.let { Arrays.hashCode(it) } ?: 0)
        result = 31 * result + (Carriers?.let { Arrays.hashCode(it) } ?: 0)
        return result
    }
}