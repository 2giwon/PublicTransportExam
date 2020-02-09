package com.egiwon.publictransport.data

import com.egiwon.publictransport.data.local.BusServiceLocalDataSource
import com.egiwon.publictransport.data.local.model.BusStations
import com.egiwon.publictransport.data.remote.BusServiceRemoteDataSource
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import com.egiwon.publictransport.data.response.Item
import io.reactivex.Single

class BusServiceRepositoryImpl(
    private val remoteDataSource: BusServiceRemoteDataSource,
    private val localDataSource: BusServiceLocalDataSource
) : BusServiceRepository {

    override fun getStationInfo(stationName: String): Single<List<Item>> =
        getStationFromRemote(stationName)

    override fun getStationCache(): Single<BusStations> = localDataSource.getBusStationsCache()

    private fun getStationFromLocal(stationName: String): Single<List<Item>> =
        localDataSource.getBusStations(stationName)

    private fun getStationFromRemote(stationName: String): Single<List<Item>> =
        remoteDataSource.getRemoteBusStationInfo(stationName)
            .doOnSuccess { items -> localDataSource.insertBusStation(stationName, items) }

    override fun getBusStationArrivalInfo(arsId: String): Single<List<ArrivalInfoItem>> =
        remoteDataSource.getBusStationArrivalInfo(arsId)

    companion object {
        private var instance: BusServiceRepositoryImpl? = null

        fun getInstance(
            remoteDataSource: BusServiceRemoteDataSource,
            localDataSource: BusServiceLocalDataSource
        ): BusServiceRepositoryImpl =
            instance ?: BusServiceRepositoryImpl(
                remoteDataSource,
                localDataSource
            ).apply {
                instance = this
            }

    }
}