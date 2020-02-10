package com.egiwon.publictransport.data.local

import com.egiwon.publictransport.data.local.model.BusStations
import io.reactivex.Single

interface BusServiceLocalDataSource {
    fun getBusStations(query: String): Single<BusStations>

    fun insertBusStation(busStations: BusStations)

    fun deleteBusStation(busStations: BusStations)
}