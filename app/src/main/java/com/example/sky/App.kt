package com.example.sky

import android.app.Application
import android.content.Context
import android.support.annotation.VisibleForTesting
import com.example.sky.dagger.glide.DaggerGlideComponent
import com.example.sky.dagger.glide.GlideComponent
import com.example.sky.dagger.glide.GlideModule
import com.example.sky.dagger.network.*
import com.example.sky.network.RestApi
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber


class App : Application() {

    @VisibleForTesting
    companion object {
        lateinit var netComponent: NetComponent
        lateinit var glideComponent: GlideComponent
    }

    fun setNetComponent(netComponent: NetComponent){
        App.netComponent = netComponent
    }

    fun createNetComponent(baseUrl: String): NetComponent {
        return DaggerNetComponent.builder()
                .okHttpModule(OkHttpModule())
                .formatInterceptorModule(FormatInterceptorModule())
                .apiKeyInterceptorModule(ApiKeyInterceptorModule())
                .gsonConverterFactoryModule(GsonConverterFactoryModule())
                .loggingInterceptorModule(LoggingInterceptorModule())
                .restApiModule(RestApiModule())
                .retrofitModule(RetrofitModule(baseUrl))
                .build()
    }

    fun createGlideComponent(context: Context): GlideComponent {
        return DaggerGlideComponent.builder()
                .glideModule(GlideModule(context))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        netComponent = createNetComponent(RestApi.SKY_URL_BASE)
        glideComponent = createGlideComponent(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.tag("Sky")
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

}