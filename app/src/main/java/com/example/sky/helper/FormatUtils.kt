package com.example.sky.helper

import android.content.Context
import com.example.sky.R
import com.example.sky.model.Carrier
import com.example.sky.model.Leg

fun getStopsCount(leg: Leg?, context: Context): String? {
    if (leg?.Stops?.isNotEmpty() == true){
        return leg?.Stops?.size.toString()
    }
    return context.getString(R.string.direct)
}

fun formatMinutesToHoursMinutes(minutes: String?): String? {
    val hours: Int = minutes?.toInt()?.div(60) ?: 0
    val minutes: Int = minutes?.toInt()?.rem(60) ?: 0
    return hours.toString() + "h " + minutes.toString() + "m"
}

fun buildFaviconUrl(carrier: Carrier?): String {
    carrier?.Code.let { return "https://logos.skyscnr.com/images/airlines/favicon/$it.png" }
}