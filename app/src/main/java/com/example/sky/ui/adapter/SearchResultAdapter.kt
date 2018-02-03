package com.example.sky.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.RequestManager
import com.example.sky.App
import com.example.sky.R
import com.example.sky.helper.getTimeFromDateTimeString
import com.example.sky.model.Itinerary
import com.example.sky.model.Leg
import com.example.sky.model.Photo
import com.example.sky.model.SearchResponse
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.search_item.view.*
import javax.inject.Inject

class SearchResultAdapter(val searchResponse: SearchResponse, val context: Context) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>(){

    @Inject lateinit var glideReqManager: RequestManager
    private val clickSubject = PublishSubject.create<Pair<Photo, View>>()
    val clickEvent: Observable<Pair<Photo, View>> = clickSubject

    private val legsMap: HashMap<String, Leg> = HashMap()

    init {
        App.glideComponent.inject(this)
        for ( leg in searchResponse.Legs!!){
            leg.Id?.let { legsMap.put(it, leg) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.search_item, parent, false)
        return SearchResultViewHolder(glideReqManager, view, context)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder?, position: Int) {
        holder?.bind(searchResponse.Itineraries!![position])
    }

    override fun getItemCount(): Int = searchResponse.Itineraries!!.size

    inner class SearchResultViewHolder(val glideReqManager: RequestManager, val view: View, val context: Context) : RecyclerView.ViewHolder(view){

        init {
//            itemView.setOnClickListener {
//                clickSubject.onNext(Pair<Photo, View>(itineraries.photo!!.get(layoutPosition), view))
//            }
        }

        fun bind(itinerary: Itinerary?){
//            glideReqManager.load(photo?.getUrl()).into(itemView.findViewById(R.id.carrier_image)

            val outBoundDepartureArrival = getTimeFromDateTimeString( legsMap.get(itinerary?.OutboundLegId)?.Departure ?: "") + " - " +
                    getTimeFromDateTimeString(legsMap.get(itinerary?.OutboundLegId)?.Arrival ?: "")
            val inBoundDepartureArrival = getTimeFromDateTimeString( legsMap.get(itinerary?.InboundLegId)?.Departure ?: "") + " - " +
                    getTimeFromDateTimeString(legsMap.get(itinerary?.InboundLegId)?.Arrival ?: "")

            val row1 = view.findViewById<View>(R.id.info_row_1)
            val row2 = view.findViewById<View>(R.id.info_row_2)
            row1.findViewById<TextView>(R.id.leg_departure_arrival_time).text = outBoundDepartureArrival
            row2.findViewById<TextView>(R.id.leg_departure_arrival_time).text = inBoundDepartureArrival

        }

    }

}