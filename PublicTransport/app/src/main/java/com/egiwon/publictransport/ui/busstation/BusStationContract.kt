package com.egiwon.publictransport.ui.busstation

import com.egiwon.publictransport.base.BaseContract
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.data.response.Item

interface BusStationContract : BaseContract {

    interface View : BaseContract.View {
        fun showSearchBusStationResult(resultList: List<BusStation>)

        fun showErrorSearchNameEmpty()

        fun showErrorLoadBusStationFail()

        fun showErrorResultEmpty()

        fun showSearchBusCache(resultList: List<BusStation>, searchQuery: String)

        fun sendFavoriteBusStation(station: Item)
    }

    interface Presenter : BaseContract.Presenter {
        fun requestBusStations()

        fun requestBusStations(stationName: String)
    }
}