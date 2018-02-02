package com.example.sky.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.example.sky.App
import com.example.sky.R
import com.example.sky.model.Itinerary
import com.example.sky.model.Photo
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.search_item.view.*
import javax.inject.Inject

class SearchResultAdapter(val itineraries: Array<Itinerary>, val context: Context) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>(){

    @Inject lateinit var glideReqManager: RequestManager
    private val clickSubject = PublishSubject.create<Pair<Photo, View>>()
    val clickEvent: Observable<Pair<Photo, View>> = clickSubject

    init {
        App.glideComponent.inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.search_item, parent, false)
        return SearchResultViewHolder(glideReqManager, view, context)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder?, position: Int) {
        holder?.bind(itineraries[position])
    }

    override fun getItemCount(): Int = itineraries.size

    inner class SearchResultViewHolder(val glideReqManager: RequestManager, view: View, val context: Context) : RecyclerView.ViewHolder(view){

        init {
//            itemView.setOnClickListener {
//                clickSubject.onNext(Pair<Photo, View>(itineraries.photo!!.get(layoutPosition), view))
//            }
        }

        fun bind(itinerary: Itinerary?){
            itemView.outbound_leg_id.text = itinerary?.OutboundLegId
            itemView.inbound_leg_id.text = itinerary?.InboundLegId
//            glideReqManager.load(photo?.getUrl()).into(itemView.findViewById(R.id.carrier_image)
        }

    }

}