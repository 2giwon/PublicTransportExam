package com.egiwon.publictransport

import com.egiwon.publictransport.base.BasePresenter
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.data.local.model.BusStations

class MainPresenter(
    private val view: MainContract.View
) : BasePresenter<BusStation>(), MainContract.Presenter {

    private val busStationList = mutableListOf<BusStation>()
    private var lastSearchQuery = ""

    override fun requestBusStationCache(block: (BusStations) -> Unit) {
        block(BusStations(lastSearchQuery, busStationList))
    }

    override fun getSearchBusStationResult(block: () -> BusStations) {
        block().run {
            busStationList.setItems(busStations)
            lastSearchQuery = searchQuery
        }
    }

}