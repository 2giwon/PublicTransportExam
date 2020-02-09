package com.egiwon.publictransport.data.local

import com.egiwon.publictransport.data.local.model.BusStations
import com.egiwon.publictransport.data.response.Item
import io.reactivex.Single

interface BusServiceLocalDataSource {
    fun getBusStations(query: String): Single<List<Item>>

    fun getBusStationsCache(): Single<BusStations>

    fun insertBusStation(query: String, busStations: List<Item>)

}