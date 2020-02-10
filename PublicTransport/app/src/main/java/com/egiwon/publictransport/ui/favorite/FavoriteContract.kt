package com.egiwon.publictransport.ui.favorite

import com.egiwon.publictransport.base.BaseContract
import com.egiwon.publictransport.data.local.model.BusStation

interface FavoriteContract : BaseContract {

    interface View : BaseContract.View {
        fun showFavoriteStationList(favoriteBusStations: List<BusStation>)

        fun errorFavoriteStationsLoadFail()
    }

    interface Presenter : BaseContract.Presenter {
        fun requestFavoriteStationList()
    }
}