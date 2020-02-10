package com.egiwon.publictransport

import com.egiwon.publictransport.base.BaseContract
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.data.local.model.BusStations
import io.reactivex.subjects.BehaviorSubject

interface MainContract : BaseContract {

    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter {
        fun requestFavoriteSubject(block: (BehaviorSubject<BusStation>) -> Unit)

        fun requestFavoriteList(block: (List<BusStation>) -> Unit)

        fun requestBusStationCache(block: (BusStations) -> Unit)

        fun getSearchBusStationResult(block: () -> BusStations)
    }
}