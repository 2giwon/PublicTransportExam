package com.egiwon.publictransport.data

import com.egiwon.publictransport.data.response.Item
import io.reactivex.Single

interface BusServiceDataSource {
    fun getRemoteBusStationInfo(stationName: String): Single<List<Item>>
}