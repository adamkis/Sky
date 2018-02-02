package com.example.sky.dagger.network

import com.example.sky.ui.activity.MainActivity
import com.example.sky.ui.fragment.RecentsFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(
        OkHttpModule::class,
        FormatInterceptorModule::class,
        ApiKeyInterceptorModule::class,
        GsonConverterFactoryModule::class,
        LoggingInterceptorModule::class,
        RestApiModule::class,
        RetrofitModule::class))
interface NetComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(recentsFragment: RecentsFragment)
}
