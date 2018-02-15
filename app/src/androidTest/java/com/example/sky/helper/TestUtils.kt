package com.example.sky.helper

import android.support.test.InstrumentationRegistry
import android.R.raw
import android.content.Context
import android.support.annotation.RawRes
import com.example.sky.R
import java.io.IOException


object TestUtils{

    fun getString(id: Int): String {
        return InstrumentationRegistry.getTargetContext().resources.getString(id)
    }

    fun readRawToString(context: Context, @RawRes rawId: Int): String?{
        try {
            val res = context.resources
            val inStream = res.openRawResource(rawId)
            val b = ByteArray(inStream.available())
            inStream.read(b)
            return String(b)
        } catch (e: IOException) {
            logThrowable(e)
        }
        return null
    }

}