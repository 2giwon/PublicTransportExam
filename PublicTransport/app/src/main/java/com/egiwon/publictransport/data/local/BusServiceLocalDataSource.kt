package com.egiwon.publictransport.data.local

import com.egiwon.publictransport.data.response.ArrivalInfoItem
import com.egiwon.publictransport.data.response.Item
import io.reactivex.Completable
import io.reactivex.Single

interface BusServiceLocalDataSource {
    fun getFavoriteBusStations(): Single<List<Item>>

    fun getFavoriteBusStationByArsId(arsId: String): Single<Item>

    fun insertFavoriteBusStation(busStation: ArrivalInfoItem): Completable

    fun deleteFavoriteBusStation(busStation: ArrivalInfoItem): Completable
}