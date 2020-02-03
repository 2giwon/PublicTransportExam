package com.egiwon.publictransport.data

import com.egiwon.publictransport.data.remote.BusServiceRemoteDataSource
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import io.reactivex.Single

class BusServiceRepositoryImpl(
    private val remoteDataSource: BusServiceRemoteDataSource
) : BusServiceRepository {

    override fun getStationInfo(stationName: String) =
        remoteDataSource.getRemoteBusStationInfo(stationName = stationName)

    override fun getBusStationArrivalInfo(arsId: String): Single<List<ArrivalInfoItem>> =
        remoteDataSource.getBusStationArrivalInfo(arsId)

    companion object {
        private var instance: BusServiceRepositoryImpl? = null

        fun getInstance(remoteDataSource: BusServiceRemoteDataSource): BusServiceRepositoryImpl =
            instance ?: BusServiceRepositoryImpl(remoteDataSource).apply {
                instance = this
            }

    }
}