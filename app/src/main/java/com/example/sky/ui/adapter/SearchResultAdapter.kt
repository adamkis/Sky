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
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.search_item.view.*
import javax.inject.Inject

class SearchResultAdapter(val searchDetails: SearchDetails, val searchResponse: SearchResponse, val context: Context) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>(){

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

    inner class SearchResultViewHolder(val glideReqManager: RequestManager, val view: View, val context: Context) : RecyclerView.ViewHolder(view){

        fun bind(itinerary: Itinerary?){

            val agent: Agent? = agentsMap[itinerary?.PricingOptions?.get(0)?.Agents?.get(0)]
            view.price.text = currencySymbol + itinerary?.PricingOptions?.get(0)?.Price
            // TODO: Into formatutils
            view.agent.text = "via " + agent?.Name

            // TODO: Clean up

            val outBoundLeg: Leg? = legsMap[itinerary?.OutboundLegId]
            val inBoundLeg: Leg? = legsMap[itinerary?.InboundLegId]
            val outBoundLegOrigin: Place? = placesMap[outBoundLeg?.OriginStation]
            val outBoundLegDestination: Place? = placesMap[outBoundLeg?.DestinationStation]
            val inBoundLegOrigin: Place? = placesMap[inBoundLeg?.OriginStation]
            val inBoundLegDestination: Place? = placesMap[inBoundLeg?.DestinationStation]
            val outBoundCarrier: Carrier? = carriersMap[outBoundLeg?.Carriers?.get(0)]
            val inBoundCarrier: Carrier? = carriersMap[inBoundLeg?.Carriers?.get(0)]

            val outBoundDepartureArrival = getTimeFromDateTimeString( outBoundLeg?.Departure ?: "") + " - " +
                    getTimeFromDateTimeString(outBoundLeg?.Arrival ?: "")
            val inBoundDepartureArrival = getTimeFromDateTimeString( inBoundLeg?.Departure ?: "") + " - " +
                    getTimeFromDateTimeString(inBoundLeg?.Arrival ?: "")

            val outBoundRow = view.findViewById<View>(R.id.info_row_1)
            outBoundRow.findViewById<TextView>(R.id.leg_departure_arrival_time).text = outBoundDepartureArrival
            outBoundRow.findViewById<TextView>(R.id.leg_details).text = context.getString(R.string.leg_detail, outBoundLegOrigin?.Code, outBoundLegDestination?.Code, outBoundCarrier?.Name)
            outBoundRow.findViewById<TextView>(R.id.leg_stops).text = getStopsCount(outBoundLeg, context)
            outBoundRow.findViewById<TextView>(R.id.leg_duration).text = formatMinutesToHoursMinutes(outBoundLeg?.Duration)
            buildFaviconUrl(outBoundCarrier).let { glideReqManager.load(it).into(outBoundRow.findViewById(R.id.carrier_image)) }

            val inBoundRow = view.findViewById<View>(R.id.info_row_2)
            inBoundRow.findViewById<TextView>(R.id.leg_departure_arrival_time).text = inBoundDepartureArrival
            inBoundRow.findViewById<TextView>(R.id.leg_details).text = context.getString(R.string.leg_detail, inBoundLegOrigin?.Code, inBoundLegDestination?.Code, inBoundCarrier?.Name)
            inBoundRow.findViewById<TextView>(R.id.leg_stops).text = getStopsCount(inBoundLeg, context)
            inBoundRow.findViewById<TextView>(R.id.leg_duration).text = formatMinutesToHoursMinutes(inBoundLeg?.Duration)
            buildFaviconUrl(inBoundCarrier).let { glideReqManager.load(it).into(inBoundRow.findViewById(R.id.carrier_image)) }

        }

    }

}