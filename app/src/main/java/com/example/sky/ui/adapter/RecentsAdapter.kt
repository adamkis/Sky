package com.example.sky.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sky.App
import com.example.sky.R
import com.example.sky.model.Photo
import com.example.sky.model.Photos
import com.bumptech.glide.RequestManager
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.recents_item.view.*
import javax.inject.Inject

class RecentsAdapter(val photos: Photos, val context: Context) : RecyclerView.Adapter<RecentsAdapter.RecentsViewHolder>(){

    @Inject lateinit var glideReqManager: RequestManager
    private val clickSubject = PublishSubject.create<Pair<Photo, View>>()
    val clickEvent: Observable<Pair<Photo, View>> = clickSubject

    init {
        App.glideComponent.inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecentsViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.recents_item, parent, false)
        return RecentsViewHolder(glideReqManager, view, context)
    }

    override fun onBindViewHolder(holder: RecentsViewHolder?, position: Int) {
        holder?.bind(photos.photo?.get(position))
    }

    override fun getItemCount(): Int = photos.photo?.size ?: 0

    inner class RecentsViewHolder(val glideReqManager: RequestManager, view: View, val context: Context) : RecyclerView.ViewHolder(view){

        init {
            itemView.setOnClickListener {
                clickSubject.onNext(Pair<Photo, View>(photos.photo!!.get(layoutPosition), view))
            }
        }

        fun bind(photo: Photo?){
            itemView.recents_photo_title.text = photo?.title
            itemView.recents_photo_id.text = photo?.id
            glideReqManager.load(photo?.getUrl()).into(itemView.findViewById(R.id.recents_image))
        }

    }

}