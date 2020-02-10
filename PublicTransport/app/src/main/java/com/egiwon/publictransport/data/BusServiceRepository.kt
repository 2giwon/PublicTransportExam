package com.egiwon.publictransport.data

import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.data.local.model.BusStations
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import io.reactivex.Completable
import io.reactivex.Single

interface BusServiceRepository {
    fun getStationInfo(stationName: String): Single<BusStations>

    fun getBusStationArrivalInfo(arsId: String): Single<List<ArrivalInfoItem>>

    fun addFavoriteBusStation(arsId: String, stationName: String): Completable

    fun getFavoriteBusStations(): Single<List<BusStation>>
}