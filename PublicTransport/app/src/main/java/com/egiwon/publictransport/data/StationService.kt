package com.egiwon.publictransport.data

import com.egiwon.publictransport.data.response.ServiceResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StationService {

    @GET("stationinfo/getStationByName")
    fun getStationInfo(
        @Query("serviceKey", encoded = true)
        serviceKey: String,
        @Query("stSrch", encoded = false)
        stationName: String
    ): Call<ServiceResult>
}