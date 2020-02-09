package com.egiwon.publictransport.data.local

import com.egiwon.publictransport.data.local.model.BusStations
import com.egiwon.publictransport.data.response.Item
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class BusServiceLocalDataSourceImpl(
    private val dao: BusStationDao
) : BusServiceLocalDataSource {
    override fun getBusStations(query: String): Single<List<Item>> =
        dao.getBusStations(query)
            .map { it.busStations }
            .onErrorReturn { emptyList() }
            .toSingle()
            .subscribeOn(Schedulers.io())

    override fun getBusStationsCache(): Single<BusStations> =
        dao.getBusStationsCache()
            .toSingle()
            .subscribeOn(Schedulers.io())

    override fun insertBusStation(query: String, busStations: List<Item>) =
        dao.insertBusStation(busStations.map {
            BusStations(query, false, busStations, System.currentTimeMillis())
        })

    companion object {
        private var instance: BusServiceLocalDataSource? = null

        fun getInstance(dao: BusStationDao): BusServiceLocalDataSource =
            instance ?: BusServiceLocalDataSourceImpl(dao).apply {
                instance = this
            }

    }

}