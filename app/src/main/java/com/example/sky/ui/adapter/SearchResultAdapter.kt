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
import com.example.sky.helper.*
import com.example.sky.model.*
import kotlinx.android.synthetic.main.search_item.view.*
import javax.inject.Inject

class SearchResultAdapter(searchDetails: SearchDetails,
                          private val searchResponse: SearchResponse,
                          private val context: Context
        ) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>(){

    @Inject lateinit var glideReqManager: RequestManager

    private val legsMap: HashMap<String, Leg> = HashMap()
    private val placesMap: HashMap<String, Place> = HashMap()
    private val carriersMap: HashMap<String, Carrier> = HashMap()
    private val agentsMap: HashMap<String, Agent> = HashMap()
    private var currencySymbol: String? = null

    init {
        App.glideComponent.inject(this)
        for ( leg in searchResponse.Legs!!){
            leg.Id?.let { legsMap.put(it, leg) }
        }
        for ( place in searchResponse.Places!!){
            place.Id?.let { placesMap.put(it, place) }
        }
        for ( carrier in searchResponse.Carriers!!){
            carrier.Id?.let { carriersMap.put(it, carrier) }
        }
        for ( agent in searchResponse.Agents!!){
            agent.Id?.let { agentsMap.put(it, agent) }
        }
        for ( currency in searchResponse.Currencies!!){
            if( currency.Code == searchDetails.currency ){
                currencySymbol = currency.Symbol
                break
            }
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

    inner class SearchResultViewHolder(private val glideReqManager: RequestManager, private val view: View, private val context: Context) : RecyclerView.ViewHolder(view){

        fun bind(itinerary: Itinerary?){
            fillAgentAndPrice(itinerary)
            fillLegInfo(legsMap[itinerary?.OutboundLegId], view.findViewById<View>(R.id.info_row_1))
            fillLegInfo(legsMap[itinerary?.InboundLegId], view.findViewById<View>(R.id.info_row_2))
        }

        private fun fillAgentAndPrice(itinerary: Itinerary?){
            val agent: Agent? = agentsMap[itinerary?.PricingOptions?.get(0)?.Agents?.get(0)]
            view.price.text = String.format("%s%s", currencySymbol, roundPrice(itinerary?.PricingOptions?.get(0)?.Price))
            view.agent.text = formatVia(agent?.Name)
        }

        private fun fillLegInfo(leg: Leg?, row: View){
            val legOrigin: Place? = placesMap[leg?.OriginStation]
            val legDestination: Place? = placesMap[leg?.DestinationStation]
            val carrier: Carrier? = carriersMap[leg?.Carriers?.get(0)]
            val departureArrival = String.format("%s - %s",
                    getTimeFromDateTimeString( leg?.Departure ?: ""),
                    getTimeFromDateTimeString(leg?.Arrival ?: ""))

            row.findViewById<TextView>(R.id.leg_departure_arrival_time).text = departureArrival
            row.findViewById<TextView>(R.id.leg_details).text = context.getString(R.string.leg_detail, legOrigin?.Code, legDestination?.Code, carrier?.Name)
            row.findViewById<TextView>(R.id.leg_stops).text = leg.getStopsCount(context)
            row.findViewById<TextView>(R.id.leg_duration).text = formatMinutesToHoursMinutes(leg?.Duration)
            buildFaviconUrl(carrier).let { glideReqManager.load(it).into(row.findViewById(R.id.carrier_image)) }
        }

    }

}