package com.example.sky.dagger.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GlideModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideGlide(): RequestManager {
        return Glide.with(context)
    }
}