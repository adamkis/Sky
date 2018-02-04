package com.example.sky.helper

import android.content.Context
import com.example.sky.R
import com.example.sky.model.Carrier
import com.example.sky.model.Leg
import com.example.sky.model.SearchDetails

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

fun formatSearchDetails(searchDetails: SearchDetails?, context: Context): String? {
    val outDayMonth = getDayMonth(searchDetails?.outbounddate)
    val inDayMonth = getDayMonth(searchDetails?.inbounddate)
    val adultsCount: Int = searchDetails?.adults?.toInt() ?: 0
    val passengers = context.resources.getQuantityString(R.plurals.number_of_adults, adultsCount, adultsCount)
    val cabinClass = searchDetails?.cabinclass
    return context.getString(R.string.search_title_details, outDayMonth, inDayMonth, passengers, cabinClass)
}


fun roundPrice(priceString: String?): String {
    var roundedPrice = priceString
    try {
        roundedPrice = priceString?.toDouble()?.toInt().toString()
    }
    catch (e: NumberFormatException){
        logThrowable(e)
    }
    return roundedPrice ?: ""
}

