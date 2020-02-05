package com.egiwon.publictransport.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.egiwon.publictransport.R
import com.egiwon.publictransport.data.response.Item
import com.egiwon.publictransport.ext.toStationId
import kotlinx.android.synthetic.main.rv_station_item.view.*

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteStationViewHolder>() {

    private val favoriteStationList = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteStationViewHolder =
        FavoriteStationViewHolder(parent = parent)

    override fun getItemCount(): Int = favoriteStationList.size

    override fun onBindViewHolder(holder: FavoriteStationViewHolder, position: Int) =
        holder.bind(favoriteStationList[position])

    fun setItems(list: List<Item>) {
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
        fun bind(item: Item) = itemView.bindItem(item)

        private fun View.bindItem(item: Item) {
            tv_station_name.text = item.stNm
            tv_station_arsId.text = item.arsId.toStationId()
        }

    }
}