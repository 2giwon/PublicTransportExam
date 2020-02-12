package com.egiwon.publictransport

import com.egiwon.publictransport.base.BaseContract
import com.egiwon.publictransport.data.local.model.BusStations

interface MainContract : BaseContract {

    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter {
        fun requestBusStationCache(block: (BusStations) -> Unit)

        fun getSearchBusStationResult(block: () -> BusStations)
    }
}