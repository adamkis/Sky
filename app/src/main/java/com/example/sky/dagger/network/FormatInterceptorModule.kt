package com.example.sky.dagger.network

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton


@Module
class FormatInterceptorModule() {
    @Provides
    @Singleton
    @Named("format")
    fun provideFormatInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            val url = request.url().newBuilder()
                    .addQueryParameter("format", "json")
                    .addQueryParameter("nojsoncallback", "1")
                    .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
    }
}