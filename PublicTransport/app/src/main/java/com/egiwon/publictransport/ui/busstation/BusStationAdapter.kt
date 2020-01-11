package com.egiwon.publictransport.ui.busstation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.egiwon.publictransport.R
import com.egiwon.publictransport.data.response.Item
import com.egiwon.publictransport.data.response.ServiceResult
import kotlinx.android.synthetic.main.rv_station_item.view.*

class BusStationAdapter(private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<BusStationAdapter.StationViewHolder>() {

    private val stationList = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder =
        StationViewHolder(parent = parent).apply {
            itemView.cb_favourite.setOnClickListener {
                itemView.cb_favourite.isChecked = true
                onClick(stationList[adapterPosition].stId)
            }
        }

    override fun getItemCount(): Int = stationList.size

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) =
        holder.bind(stationList[position])

    fun setItems(resultItem: ServiceResult) {
        stationList.clear()
        resultItem.msgBody?.itemList?.forEach { stationList.add(it) }
        notifyDataSetChanged()
    }

    inner class StationViewHolder(
        @LayoutRes
        layoutRes: Int = R.layout.rv_station_item,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
    ) {
        fun bind(item: Item) = itemView.bindItem(item)

        private fun View.bindItem(item: Item) {
            tv_station_name.text = item.stNm
            tv_station_id.text = item.stId
            tv_station_arsId.text = item.arsId.toString()
        }

    }
}