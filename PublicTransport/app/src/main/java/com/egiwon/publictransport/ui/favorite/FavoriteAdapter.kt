package com.egiwon.publictransport.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.forEachIndexed
import androidx.recyclerview.widget.RecyclerView
import com.egiwon.publictransport.R
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.ext.toStationId
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.rv_station_item.view.*
import java.util.*

typealias onGetItemListener = (position: Int) -> BusStation
typealias onClickListener = (BusStation) -> Unit
typealias onRemoveItemListener = (position: Int) -> Unit
typealias onMovedItemListener = (List<BusStation>) -> Unit
typealias onCheckedTagListener = (adapterPosition: Int, chipsPosition: Int) -> Unit


class FavoriteAdapter(
    private val onClick: onClickListener,
    private val onMoved: onMovedItemListener,
    private val onCheckedTag: onCheckedTagListener
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

    private fun ChipGroup.getCheckedItem(checkedView: View): Int {
        forEachIndexed { index, view ->
            if (view.id == checkedView.id) {
                return index
            }
        }

        return -1
    }

    fun moveItems(from: Int, to: Int) {
        Collections.swap(favoriteStationList, from, to)
        notifyItemMoved(from, to)
        if (from > to) {
            onMoved(favoriteStationList.subList(to, from + 1))
        } else {
            onMoved(favoriteStationList.subList(from, to + 1))
        }
    }

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