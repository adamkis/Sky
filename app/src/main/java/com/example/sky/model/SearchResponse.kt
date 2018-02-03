package com.example.sky.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@SuppressLint("ParcelCreator")
@Parcelize
data class SearchResponse(
        var SessionKey: String? = null,
        var Status: String? = null,
        var Itineraries: Array<Itinerary>? = null,
        var Legs: Array<Leg>? = null,
        var Places: Array<Place>? = null,
        var Carriers: Array<Carrier>? = null,
        var Agents: Array<Agent>? = null,
        var Currencies: Array<Currency>? = null
    ) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchResponse

        if (SessionKey != other.SessionKey) return false
        if (Status != other.Status) return false
        if (!Arrays.equals(Itineraries, other.Itineraries)) return false
        if (!Arrays.equals(Legs, other.Legs)) return false
        if (!Arrays.equals(Places, other.Places)) return false
        if (!Arrays.equals(Carriers, other.Carriers)) return false
        if (!Arrays.equals(Agents, other.Agents)) return false
        if (!Arrays.equals(Currencies, other.Currencies)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = SessionKey?.hashCode() ?: 0
        result = 31 * result + (Status?.hashCode() ?: 0)
        result = 31 * result + (Itineraries?.let { Arrays.hashCode(it) } ?: 0)
        result = 31 * result + (Legs?.let { Arrays.hashCode(it) } ?: 0)
        result = 31 * result + (Places?.let { Arrays.hashCode(it) } ?: 0)
        result = 31 * result + (Carriers?.let { Arrays.hashCode(it) } ?: 0)
        result = 31 * result + (Agents?.let { Arrays.hashCode(it) } ?: 0)
        result = 31 * result + (Currencies?.let { Arrays.hashCode(it) } ?: 0)
        return result
    }
}