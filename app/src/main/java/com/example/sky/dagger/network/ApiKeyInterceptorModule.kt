package com.example.sky.dagger.network

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton


@Module
class ApiKeyInterceptorModule() {
    @Provides
    @Singleton
    @Named("apiKey")
    fun provideApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
//            val url = request.url().newBuilder()
//                    .addQueryParameter("api_key", SecretKeys.FLICKR_KEY)
//                    .build()
//            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
    }
}