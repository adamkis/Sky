package com.example.sky.helper

import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Created by adam on 2018. 02. 02..
 */


fun getStackTrace(throwable: Throwable): String {
    val sw = StringWriter()
    val pw = PrintWriter(sw, true)
    throwable.printStackTrace(pw)
    return sw.buffer.toString()
}
