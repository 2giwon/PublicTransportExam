package com.egiwon.publictransport.ui.busstation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.egiwon.publictransport.R
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.ext.toStationId
import kotlinx.android.synthetic.main.rv_station_item.view.*

class BusStationAdapter(private val onClick: (BusStation) -> Unit) :
    RecyclerView.Adapter<BusStationAdapter.StationViewHolder>() {

    private val stationList = ArrayList<BusStation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder =
        StationViewHolder(parent = parent).apply {
            itemView.setOnClickListener {
                onClick(stationList[adapterPosition])
            }
        }

    override fun getItemCount(): Int = stationList.size

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) =
        holder.bind(stationList[position])

    fun setItems(resultItems: List<BusStation>) {
        stationList.clear()
        stationList.addAll(resultItems)
        notifyDataSetChanged()
    }

    inner class StationViewHolder(
        @LayoutRes
        layoutRes: Int = R.layout.rv_station_item,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
    ) {
        fun bind(item: BusStation) = itemView.bindItem(item)

        private fun View.bindItem(item: BusStation) {
            tv_station_name.text = item.stationName
            tv_station_arsId.text = item.arsId.toStationId()
        }

    }
}