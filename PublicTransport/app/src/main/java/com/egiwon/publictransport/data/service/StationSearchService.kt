package com.egiwon.publictransport.data.service

import com.egiwon.publictransport.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object StationSearchService {
    val retrofit: StationService = Retrofit.Builder()
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

}