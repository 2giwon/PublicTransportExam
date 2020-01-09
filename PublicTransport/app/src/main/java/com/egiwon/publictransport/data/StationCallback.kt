package com.egiwon.publictransport.data

import com.egiwon.publictransport.data.response.ServiceResult

interface StationCallback {
    fun onSuccess(stationInfos: ServiceResult)
    fun onFailure(throwable: Throwable)
}