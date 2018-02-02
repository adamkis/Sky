package com.example.sky.network


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.PrintWriter
import java.io.StringWriter

val SKY_URL_BASE = "http://partners.api.skyscanner.net/apiservices/"

fun getStackTrace(throwable: Throwable): String {
    val sw = StringWriter()
    val pw = PrintWriter(sw, true)
    throwable.printStackTrace(pw)
    return sw.buffer.toString()
}