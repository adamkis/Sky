package com.example.sky.dagger.network

import com.example.sky.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton
import javax.inject.Named



@Module
class OkHttpModule() {
    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, @Named("format") formatInterceptor: Interceptor, @Named("apiKey") apiKeyInterceptor: Interceptor): OkHttpClient {
        var builder = OkHttpClient.Builder()
                .addInterceptor(formatInterceptor)
                .addInterceptor(apiKeyInterceptor)
        if (BuildConfig.DEBUG){
            builder = builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }
}