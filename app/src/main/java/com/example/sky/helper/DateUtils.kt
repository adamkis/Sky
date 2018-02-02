package com.example.sky.helper

import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun getTimeFromDateTimeString(dateTime: String): String? {
    val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val hoursMinutesFormat = SimpleDateFormat("HH:mm")
    val date: Date?
    var timeString: String? = null
    try {
        date = dateTimeFormat.parse(dateTime)
        Timber.d("")
        timeString = hoursMinutesFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return timeString
}