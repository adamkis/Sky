package com.example.sky.dagger.network

import android.app.Application
import com.example.sky.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class OkHttpModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,
                            application: Application
        ): OkHttpClient {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(application.cacheDir, cacheSize)
        var builder = OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG){
            builder = builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }
}