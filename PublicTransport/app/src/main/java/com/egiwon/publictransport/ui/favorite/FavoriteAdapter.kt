package com.egiwon.publictransport.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.egiwon.publictransport.R
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.ext.toStationId
import kotlinx.android.synthetic.main.rv_station_item.view.*
import java.util.*
import kotlin.math.max
import kotlin.math.min

typealias onGetItemListener = (position: Int) -> BusStation
typealias onClickListener = (BusStation) -> Unit
typealias onRemoveItemListener = (position: Int) -> Unit
typealias onMovedItemListener = (List<BusStation>) -> Unit

class FavoriteAdapter(
    private val onClick: onClickListener,
    private val onMoved: onMovedItemListener
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteStationViewHolder>() {

    private val favoriteStationList = mutableListOf<BusStation>()

    val onGetItem: onGetItemListener = { favoriteStationList[it] }

    val onRemoveItem: onRemoveItemListener = { favoriteStationList.removeAt(it) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteStationViewHolder =
        FavoriteStationViewHolder(parent = parent).apply {
            itemView.setOnClickListener {
                onClick(favoriteStationList[adapterPosition])
            }
        }

    override fun getItemCount(): Int = favoriteStationList.size

    override fun onBindViewHolder(holder: FavoriteStationViewHolder, position: Int) =
        holder.bind(favoriteStationList[position])

    fun moveItems(from: Int, to: Int) {
        Collections.swap(favoriteStationList, from, to)
        notifyItemMoved(from, to)
    }

    fun onEndMovedItem(start: Int, end: Int) {
        val subList = mutableListOf<BusStation>()
        subList.addAll(favoriteStationList.subList(min(start, end), max(start, end)))
        subList.add(favoriteStationList[max(start, end)])

        onMoved(subList)
    }

    fun setItems(list: List<BusStation>) {
        val sets = mutableSetOf<BusStation>().apply { addAll(list) }
        favoriteStationList.clear()
        favoriteStationList.addAll(sets)
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