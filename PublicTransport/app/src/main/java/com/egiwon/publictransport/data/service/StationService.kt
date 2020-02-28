package com.egiwon.publictransport.data.service

import com.egiwon.publictransport.data.response.BusStationArrivalResult
import com.egiwon.publictransport.data.response.BusStationRouteInfo
import com.egiwon.publictransport.data.response.ServiceResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface StationService {

    @GET("api/rest/stationinfo/getStationByName")
    fun getStationInfo(
        @Query("serviceKey", encoded = true)
        serviceKey: String,
        @Query("stSrch", encoded = false)
        stationName: String
    ): Single<ServiceResult>

    @GET("api/rest/stationinfo/getStationByUid")
    fun getStationArrivalInfo(
        @Query("serviceKey", encoded = true)
        serviceKey: String,
        @Query("arsId", encoded = false)
        stationId: String
    ): Single<BusStationArrivalResult>

    @GET("api/rest/stationinfo/getRouteByStation")
    fun getRouteByStation(
        @Query("serviceKey", encoded = true)
        serviceKey: String,
        @Query("arsId", encoded = false)
        stationId: String
    ): Single<BusStationRouteInfo>
}