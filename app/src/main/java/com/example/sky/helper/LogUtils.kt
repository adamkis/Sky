package com.example.sky.helper

import timber.log.Timber
import java.io.PrintWriter
import java.io.StringWriter

fun logThrowable(t: Throwable) {
    Timber.e(t)
    // TODO: send logs to analytics and servers
}

fun logDebug(s: String) {
    Timber.d(s)
    // TODO: send logs to analytics and servers
}

fun getStackTrace(throwable: Throwable): String {
    val sw = StringWriter()
    val pw = PrintWriter(sw, true)
    throwable.printStackTrace(pw)
    return sw.buffer.toString()
}
