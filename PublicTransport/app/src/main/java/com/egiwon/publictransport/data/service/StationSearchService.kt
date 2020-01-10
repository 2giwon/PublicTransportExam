package com.egiwon.publictransport.data.service

import com.egiwon.publictransport.BuildConfig
import com.egiwon.publictransport.data.StationCallback
import com.egiwon.publictransport.data.StationService
import com.egiwon.publictransport.data.response.ServiceResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class StationSearchService(
    val receiver: StationCallback
) {
    private val retrofit: StationService = Retrofit.Builder()
        .baseUrl("http://ws.bus.go.kr/api/rest/")
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = if (BuildConfig.DEBUG) {
                            HttpLoggingInterceptor.Level.BODY
                        } else {
                            HttpLoggingInterceptor.Level.NONE
                        }
                    }
                )
                .build()
        )
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()
        .create(StationService::class.java)


    fun getStationInfo(stationName: String, serviceKey: String) {
        try {
            retrofit.getStationInfo(
                serviceKey,
                stationName
            ).enqueue(object : Callback<ServiceResult> {
                override fun onFailure(call: Call<ServiceResult>, t: Throwable) =
                    receiver.onFailure(t)

                override fun onResponse(
                    call: Call<ServiceResult>,
                    response: Response<ServiceResult>
                ) {
                    response.body()?.let {
                        receiver.onSuccess(it)
                    }
                }
            })
        } catch (e: BootstrapMethodError) {
            receiver.onFailure(Throwable())
        }
    }

}