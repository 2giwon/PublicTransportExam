package com.egiwon.publictransport.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.egiwon.publictransport.R
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.ext.toStationId
import kotlinx.android.synthetic.main.rv_fv_station_item.view.*
import kotlinx.android.synthetic.main.rv_station_item.view.tv_station_arsId
import kotlinx.android.synthetic.main.rv_station_item.view.tv_station_name

class FavoriteAdapter(
    private val onClick: (BusStation) -> Unit,
    private val onDeleteClick: (BusStation) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteStationViewHolder>() {

    private val favoriteStationList = ArrayList<BusStation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteStationViewHolder =
        FavoriteStationViewHolder(parent = parent).apply {
            itemView.layout_fv_bus_station.setOnClickListener {
                onClick(favoriteStationList[adapterPosition])
            }

            itemView.iv_delete_item.setOnClickListener {
                onDeleteClick(favoriteStationList[adapterPosition])
            }
        }

    override fun getItemCount(): Int = favoriteStationList.size

    override fun onBindViewHolder(holder: FavoriteStationViewHolder, position: Int) =
        holder.bind(favoriteStationList[position])

    fun setItems(list: List<BusStation>) {
        favoriteStationList.clear()
        favoriteStationList.addAll(list)
        notifyDataSetChanged()
    }

    inner class FavoriteStationViewHolder(
        @LayoutRes
        layoutRes: Int = R.layout.rv_fv_station_item,
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