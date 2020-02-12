package com.egiwon.publictransport.data.local

import com.egiwon.publictransport.data.local.model.BusStation
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class BusServiceLocalDataSourceImpl(
    private val dao: BusStationDao
) : BusServiceLocalDataSource {

    override fun getFavoriteBusStation(): Single<List<BusStation>> =
        dao.getFavoriteBusStations()
            .toSingle()
            .subscribeOn(Schedulers.io())

    override fun insertBusStation(busStation: BusStation): Completable =
        dao.insertBusStation(busStation)
            .subscribeOn(Schedulers.io())

    override fun deleteBusStation(busStation: BusStation): Completable =
        dao.deleteBusStations(busStation)
            .subscribeOn(Schedulers.io())

    companion object {
        private var instance: BusServiceLocalDataSource? = null

        fun getInstance(dao: BusStationDao): BusServiceLocalDataSource =
            instance ?: BusServiceLocalDataSourceImpl(dao).apply {
                instance = this
            }

    }

}