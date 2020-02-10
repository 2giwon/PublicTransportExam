package com.egiwon.publictransport.data.local

import com.egiwon.publictransport.data.local.model.BusStations
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class BusServiceLocalDataSourceImpl(
    private val dao: BusStationDao
) : BusServiceLocalDataSource {
    override fun getBusStations(query: String): Single<BusStations> =
        dao.getBusStations(query)
            .toSingle()
            .subscribeOn(Schedulers.io())

    override fun getBusStationsCache(): Single<BusStations> =
        dao.getBusStationsCache()
            .toSingle()
            .subscribeOn(Schedulers.io())

    override fun insertBusStation(busStations: BusStations) =
        dao.insertBusStation(busStations)

    override fun deleteBusStation(busStations: BusStations) =
        dao.deleteBusStations(busStations)

    companion object {
        private var instance: BusServiceLocalDataSource? = null

        fun getInstance(dao: BusStationDao): BusServiceLocalDataSource =
            instance ?: BusServiceLocalDataSourceImpl(dao).apply {
                instance = this
            }

    }

}