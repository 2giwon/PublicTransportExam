package com.egiwon.publictransport.data

import com.egiwon.publictransport.data.remote.BusServiceRemoteDataSource

class BusServiceRepositoryImpl(
    private val remoteDataSource: BusServiceRemoteDataSource
) : BusServiceRepository {

    override fun getStationInfo(stationName: String) =
        remoteDataSource.getRemoteBusStationInfo(stationName = stationName)

    companion object {
        private var instance: BusServiceRepositoryImpl? = null

        fun getInstance(remoteDataSource: BusServiceRemoteDataSource): BusServiceRepositoryImpl =
            instance ?: BusServiceRepositoryImpl(remoteDataSource).apply {
                instance = this
            }

    }
}