package com.egiwon.publictransport.data

import com.egiwon.publictransport.data.local.BusServiceLocalDataSource
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.data.local.model.BusStations
import com.egiwon.publictransport.data.remote.BusServiceRemoteDataSource
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import com.egiwon.publictransport.data.response.BusStationRouteInfoItem
import io.reactivex.Completable
import io.reactivex.Single

class BusServiceRepositoryImpl(
    private val remoteDataSource: BusServiceRemoteDataSource,
    private val localDataSource: BusServiceLocalDataSource
) : BusServiceRepository {

    override fun getStationInfo(stationName: String): Single<BusStations> =
        getStationFromRemote(stationName)

    private fun getStationFromRemote(stationName: String): Single<BusStations> =
        remoteDataSource.getRemoteBusStationInfo(stationName)
            .map { items ->
                BusStations(stationName, items.mapIndexed { index, item ->
                    BusStation(index, item.arsId, item.stNm, "")
                })
            }

    override fun getBusStationArrivalInfo(arsId: String): Single<List<ArrivalInfoItem>> =
        remoteDataSource.getBusStationArrivalInfo(arsId)

    override fun getFavoriteBusStation(id: Int): Single<BusStation> =
        localDataSource.getFavoriteBusStation(id)

    override fun addFavoriteBusStation(busStation: BusStation) =
        localDataSource.insertBusStation(busStation)

    override fun deleteFavoriteBusStation(busStation: BusStation): Completable =
        localDataSource.deleteBusStation(busStation)

    override fun getFavoriteBusStations(): Single<List<BusStation>> =
        localDataSource.getFavoriteBusStation()

    override fun getFavoriteBusStations(from: Int, to: Int): Single<List<BusStation>> =
        localDataSource.getFavoriteBusStationsFromTo(from, to)

    override fun updateFavoriteBusStations(busStations: List<BusStation>): Completable =
        localDataSource.updateFavoriteBusStations(busStations)

    override fun saveBusStation(busStation: BusStation): Completable =
        localDataSource.updateBusStation(busStation)

    override fun getFavoriteBusStationLastIndex(): Single<Int> =
        localDataSource.getLastBusStationIndex()

    override fun getBusRouteInfo(arsId: String): Single<List<BusStationRouteInfoItem>> =
        remoteDataSource.getBusStationRouteInfo(arsId)

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