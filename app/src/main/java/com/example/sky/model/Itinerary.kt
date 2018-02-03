package com.example.sky.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@SuppressLint("ParcelCreator")
@Parcelize
data class Itinerary(
        var OutboundLegId: String? = null,
        var InboundLegId: String? = null,
        var PricingOptions: Array<PricingOption>? = null
    ) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Itinerary

        if (OutboundLegId != other.OutboundLegId) return false
        if (InboundLegId != other.InboundLegId) return false
        if (!Arrays.equals(PricingOptions, other.PricingOptions)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = OutboundLegId?.hashCode() ?: 0
        result = 31 * result + (InboundLegId?.hashCode() ?: 0)
        result = 31 * result + (PricingOptions?.let { Arrays.hashCode(it) } ?: 0)
        return result
    }
}
