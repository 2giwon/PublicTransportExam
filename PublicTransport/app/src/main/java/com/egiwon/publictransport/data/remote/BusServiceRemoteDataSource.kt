package com.egiwon.publictransport.data.remote

import com.egiwon.publictransport.data.BusServiceDataSource
import com.egiwon.publictransport.data.response.Item
import com.egiwon.publictransport.data.service.StationSearchService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class BusServiceRemoteDataSource : BusServiceDataSource {
    override fun getRemoteBusStationInfo(
        stationName: String
    ): Single<List<Item>> =
        StationSearchService.retrofit.getStationInfo(
            serviceKey = SERVICE_KEY,
            stationName = stationName
        )
            .map { it.msgBody?.itemList?.toList() ?: emptyList() }
            .subscribeOn(Schedulers.io())


    companion object {
        private const val SERVICE_KEY =
            "zdPk2xTv930O2l4zxAjyh%2BCZeZKCY3UYhKsoFTlOfAvhCcEt0DdPZFxfQFsDrQgWLcTQHPunYimxI9WnTm3PFQ%3D%3D"


        private var instance: BusServiceRemoteDataSource? = null

        fun getInstance(): BusServiceRemoteDataSource =
            instance ?: BusServiceRemoteDataSource().apply {
                instance = this
            }
    }
}