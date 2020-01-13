package com.egiwon.publictransport.data

import com.egiwon.publictransport.data.response.Item

interface StationCallback {
    fun onSuccess(stationInfos: List<Item>)
    fun onFailure(throwable: Throwable)
}