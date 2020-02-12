package com.egiwon.publictransport.ui.busstation

import com.egiwon.publictransport.base.BaseContract
import com.egiwon.publictransport.data.local.model.BusStations

interface BusStationContract : BaseContract {

    interface View : BaseContract.View {
        fun showSearchBusStationResult(busStations: BusStations)

        fun showErrorSearchNameEmpty()

        fun showErrorLoadBusStationFail()

        fun showErrorResultEmpty()

        fun showSearchBusCache(busStations: BusStations)

        fun sendSearchBusStationResult(busStations: BusStations)
    }

    interface Presenter : BaseContract.Presenter {

        fun requestBusStations(stationName: String)
    }
}