package com.example.sky.dagger.network

import com.example.sky.network.RestApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class RestApiModule {
    @Provides
    @Singleton
    fun provideRestApi(retrofit: Retrofit): RestApi {
        return retrofit.create<RestApi>(RestApi::class.java)
    }
}