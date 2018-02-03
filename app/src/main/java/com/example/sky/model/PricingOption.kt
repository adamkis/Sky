package com.example.sky.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@SuppressLint("ParcelCreator")
@Parcelize
data class PricingOption(
        var Price: String? = null,
        var Agents: Array<String>? = null
    ) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PricingOption

        if (Price != other.Price) return false
        if (!Arrays.equals(Agents, other.Agents)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Price?.hashCode() ?: 0
        result = 31 * result + (Agents?.let { Arrays.hashCode(it) } ?: 0)
        return result
    }
}