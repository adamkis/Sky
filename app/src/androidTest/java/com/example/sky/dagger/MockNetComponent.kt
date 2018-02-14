package com.example.sky.dagger

import com.example.sky.dagger.network.*
import com.example.sky.search.SearchPresenter
import com.example.sky.ui.activity.SearchActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        MockOkHttpModule::class,
        GsonConverterFactoryModule::class,
        LoggingInterceptorModule::class,
        RestApiModule::class,
        RetrofitModule::class))
interface MockNetComponent : NetComponent {
    override fun inject(searchPresenter: SearchPresenter)
}