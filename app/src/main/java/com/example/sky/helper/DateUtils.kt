package com.example.sky.helper

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
        timeString = hoursMinutesFormat.format(date)
    } catch (e: ParseException) {
        logThrowable(e)
    }
    return timeString
}

fun getDayMonth(dateString: String?): String? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val dayMonthFormat = SimpleDateFormat("dd MMM")
    val date: Date?
    var dayMonthString: String? = null
    try {
        date = dateFormat.parse(dateString)
        dayMonthString = dayMonthFormat.format(date)
    } catch (e: ParseException) {
        logThrowable(e)
    }
    return dayMonthString
}

fun getDateString(date: Date): String? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    var dateString: String? = null
    try {
        dateString = dateFormat.format(date)
    } catch (e: ParseException) {
        logThrowable(e)
    }
    return dateString
}


fun getNextMondayAndNextDayReturn(): Pair<String, String>{
    val outBoundDate: Calendar = Calendar.getInstance()
    val inBoundDate: Calendar = Calendar.getInstance()
    while (outBoundDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
        outBoundDate.add(Calendar.DATE, 1)
        inBoundDate.add(Calendar.DATE, 1)
    }
    inBoundDate.add(Calendar.DATE, 1)

    var outBoundString = getDateString(outBoundDate.time) ?: ""
    var inBoundString = getDateString(inBoundDate.time) ?: ""
    return Pair(outBoundString, inBoundString)
}
