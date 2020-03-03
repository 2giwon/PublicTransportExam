package com.egiwon.publictransport.data.remote

import com.egiwon.publictransport.data.response.ArrivalInfoItem
import com.egiwon.publictransport.data.response.BusStationRouteInfoItem
import com.egiwon.publictransport.data.response.Item
import io.reactivex.Single

interface BusServiceRemoteDataSource {
    fun getRemoteBusStationInfo(stationName: String): Single<List<Item>>

    fun getBusStationArrivalInfo(arsId: String): Single<List<ArrivalInfoItem>>

    fun getBusStationRouteInfo(arsId: String): Single<List<BusStationRouteInfoItem>>
}