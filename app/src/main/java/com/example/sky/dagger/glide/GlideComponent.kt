package com.example.sky.dagger.glide

import android.support.v7.widget.RecyclerView
import com.example.sky.ui.adapter.SearchResultAdapter
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(
        GlideModule::class))
interface GlideComponent {
    fun inject(viewHolder: RecyclerView.ViewHolder)
    fun inject(recentsAdapter: SearchResultAdapter)
}