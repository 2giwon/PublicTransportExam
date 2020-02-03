package com.egiwon.publictransport.ui.arrivalinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.egiwon.publictransport.R
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import kotlinx.android.synthetic.main.item_bus_arrival.view.*


class BusStationArrivalAdapter :
    RecyclerView.Adapter<BusStationArrivalAdapter.BusStationArrivalViewHolder>() {

    private val items = mutableListOf<ArrivalInfoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStationArrivalViewHolder =
        BusStationArrivalViewHolder(parent = parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BusStationArrivalViewHolder, position: Int) =
        holder.bind(items[position])

    fun setItems(resultItems: List<ArrivalInfoItem>) {
        items.clear()
        items.addAll(resultItems)
        notifyDataSetChanged()
    }

    inner class BusStationArrivalViewHolder(
        @LayoutRes layoutResId: Int = R.layout.item_bus_arrival,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
    ) {

        fun bind(item: ArrivalInfoItem) = itemView.bindItem(item)

        private fun View.bindItem(item: ArrivalInfoItem) {
            tv_bus_title.text = item.rtNm
            btn_bus_way.text = context.getString(R.string.bus_way, item.adirection)
            btn_arrival_time.text = item.arrmsg1
            btn_next_bus.text = item.arrmsg2
        }
    }
}