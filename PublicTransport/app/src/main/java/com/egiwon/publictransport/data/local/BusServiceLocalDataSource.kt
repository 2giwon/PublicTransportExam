package com.egiwon.publictransport.data.local

import com.egiwon.publictransport.data.local.model.BusStation
import io.reactivex.Completable
import io.reactivex.Single

interface BusServiceLocalDataSource {
    fun getFavoriteBusStation(): Single<List<BusStation>>

    fun insertBusStation(busStation: BusStation): Completable

    fun deleteBusStation(busStation: BusStation): Completable
}