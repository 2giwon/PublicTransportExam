package com.egiwon.publictransport.data

import com.egiwon.publictransport.data.local.BusServiceLocalDataSource
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.data.local.model.BusStations
import com.egiwon.publictransport.data.remote.BusServiceRemoteDataSource
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import io.reactivex.Single

class BusServiceRepositoryImpl(
    private val remoteDataSource: BusServiceRemoteDataSource,
    private val localDataSource: BusServiceLocalDataSource
) : BusServiceRepository {

    override fun getStationInfo(stationName: String): Single<List<BusStation>> =
        getStationFromRemote(stationName)

    override fun getStationCache(): Single<BusStations> = localDataSource.getBusStationsCache()

    private fun getStationFromLocal(stationName: String): Single<List<BusStation>> =
        localDataSource.getBusStations(stationName)
            .onErrorReturn { BusStations("", emptyList(), 0L) }
            .map { it.busStations }

    private fun getStationFromRemote(stationName: String): Single<List<BusStation>> =
        remoteDataSource.getRemoteBusStationInfo(stationName)
            .map { responseItems ->
                responseItems.map { BusStation(it.arsId, false, it.stNm) }
            }
            .doOnSuccess { item ->
                localDataSource.insertBusStation(
                    BusStations(stationName, item, System.currentTimeMillis())
                )
            }

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