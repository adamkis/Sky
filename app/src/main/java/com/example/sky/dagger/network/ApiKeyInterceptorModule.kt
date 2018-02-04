package com.example.sky.dagger.network

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton

// TODO: Change name
// TODO: clean up
@Module
class ApiKeyInterceptorModule() {
    @Provides
    @Singleton
    @Named("apiKey")
    fun provideApiKeyInterceptor(): Interceptor {
        return Interceptor {
            chain ->
                val originalResponse = chain.proceed(chain.request())
                originalResponse.newBuilder()
//                        .header("Cache-Control", String.format("max-age=%d, only-if-cached, max-stale=%d", 120, 0))
                        .header("Cache-Control", String.format("max-age=%d, only-if-cached, max-stale=%d", 24*60*60, 0))
                        .build()
        }
    }
}