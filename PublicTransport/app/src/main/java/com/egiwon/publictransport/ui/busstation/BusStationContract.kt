package com.egiwon.publictransport.ui.busstation

import com.egiwon.publictransport.base.BaseContract
import com.egiwon.publictransport.data.response.Item

interface BusStationContract : BaseContract {

    interface View : BaseContract.View {
        fun showSearchBusStationResult(resultList: List<Item>)

        fun showErrorSearchNameEmpty()

        fun showErrorLoadBusStationFail()

        fun showErrorResultEmpty()
    }

    interface Presenter : BaseContract.Presenter {
        fun requestBusStations(stationName: String)
    }
}