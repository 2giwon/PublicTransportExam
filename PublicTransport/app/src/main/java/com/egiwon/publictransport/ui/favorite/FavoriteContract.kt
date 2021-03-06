package com.egiwon.publictransport.ui.favorite

import com.egiwon.publictransport.base.BaseContract
import com.egiwon.publictransport.data.local.model.BusStation

interface FavoriteContract : BaseContract {

    interface View : BaseContract.View {
        fun showFavoriteStationList(favoriteBusStations: List<BusStation>)

        fun errorFavoriteStationsLoadFail()

        fun refreshFavoriteAdapterList()
    }

    interface Presenter : BaseContract.Presenter {
        fun requestFavoriteStationList()

        fun restoreDeletedFavoriteStation()

        fun deleteFavoriteStation(busStation: BusStation, position: Int)

        fun updateFavoriteStationListFromTo(subList: List<BusStation>)

        fun setFavoriteStationTag(id: Int, tag: String)
    }
}