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

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavouriteStationViewHolder>() {

    private val favouriteStationList = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteStationViewHolder =
        FavouriteStationViewHolder(parent = parent)

    override fun getItemCount(): Int = favouriteStationList.size

    override fun onBindViewHolder(holder: FavouriteStationViewHolder, position: Int) =
        holder.bind(favouriteStationList[position])

    fun setItems(list: List<Item>) {
        favouriteStationList.clear()
        favouriteStationList.addAll(list)
        notifyDataSetChanged()
    }

    inner class FavouriteStationViewHolder(
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