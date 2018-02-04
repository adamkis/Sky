package com.example.sky.dagger.network

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import timber.log.Timber


// TODO: Remove
// TODO: error handling shouldn't be here
@Module
class FormatInterceptorModule() {
//    @Provides
//    @Singleton
//    @Named("format")
//    fun provideFormatInterceptor(): Interceptor {
//        return Interceptor { chain ->
//            var request = chain.request()
////            val url = request.url().newBuilder()
////                    .addQueryParameter("format", "json")
////                    .addQueryParameter("nojsoncallback", "1")
////                    .build()
////            request = request.newBuilder().url(url).build()
//            chain.proceed(request)
//        }
//    }

    @Provides
    @Singleton
    @Named("format")
    fun provideFormatInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            if (response.code() == 304) {
                Timber.d("Error code was 304")
                Timber.d(response.message())

                response
            }

            response

        }
    }
}