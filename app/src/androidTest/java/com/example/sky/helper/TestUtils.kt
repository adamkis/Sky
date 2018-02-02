package com.example.sky.helper

import android.support.test.InstrumentationRegistry


object TestUtils{

    fun getString(id: Int): String {
        return InstrumentationRegistry.getTargetContext().resources.getString(id)
    }

}