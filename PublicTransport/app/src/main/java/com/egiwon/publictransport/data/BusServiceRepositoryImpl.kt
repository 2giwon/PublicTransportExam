package com.egiwon.publictransport.data

import com.egiwon.publictransport.data.local.BusServiceLocalDataSource
import com.egiwon.publictransport.data.remote.BusServiceRemoteDataSource
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import com.egiwon.publictransport.data.response.Item
import io.reactivex.Single

class BusServiceRepositoryImpl(
    private val remoteDataSource: BusServiceRemoteDataSource,
    private val localDataSource: BusServiceLocalDataSource
) : BusServiceRepository {

    private val busStations = mutableMapOf<String, List<Item>>()

    override fun getStationInfo(stationName: String): Single<List<Item>> {
        return Single.fromCallable<List<Item>> {
            busStations[stationName]?.let { list ->
                list.takeIf { it.isNotEmpty() }
            }
        }
    }

    override fun getBusStationArrivalInfo(arsId: String): Single<List<ArrivalInfoItem>> =
        remoteDataSource.getBusStationArrivalInfo(arsId)

    override fun findFavoriteBusStationByArsId(arsId: String): Single<Item> =
        localDataSource.getFavoriteBusStationByArsId(arsId)

    override fun addFavoriteBusStationByArsId(arsId: String) {

    }


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