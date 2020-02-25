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

        fun deleteFavoriteStationTemporarily(busStation: BusStation, position: Int)

        fun deleteFavoriteStationPermanently()

        fun updateFavoriteStationList(busStations: List<BusStation>)

        fun requestBusStationTag(busStationIndex: Int, tagIndex: Int)
    }
}