package com.example.sky.dagger.network

import android.app.Application
import com.example.sky.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton


@Module
class OkHttpModule() {
    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,
                            @Named("format") formatInterceptor: Interceptor,
                            @Named("apiKey") apiKeyInterceptor: Interceptor,
                            application: Application
        ): OkHttpClient {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MB

        // TODO: clean up
//        val file = File(application.filesDir, filename)
//        val cache = Cache(application.cacheDir, cacheSize)
        val cache = Cache(application.filesDir, cacheSize)

        var builder = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(formatInterceptor)
                .addNetworkInterceptor(apiKeyInterceptor)
        if (BuildConfig.DEBUG){
            builder = builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }
}