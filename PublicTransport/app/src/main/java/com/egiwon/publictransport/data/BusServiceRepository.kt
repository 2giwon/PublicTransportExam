package com.egiwon.publictransport.data

import com.egiwon.publictransport.data.local.model.BusStations
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import com.egiwon.publictransport.data.response.Item
import io.reactivex.Single

interface BusServiceRepository {
    fun getStationInfo(stationName: String): Single<List<Item>>

    fun getStationCache(): Single<BusStations>

    fun getBusStationArrivalInfo(arsId: String): Single<List<ArrivalInfoItem>>
}