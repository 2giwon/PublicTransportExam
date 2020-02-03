package com.egiwon.publictransport.ui.arrivalinfo

import com.egiwon.publictransport.base.BaseContract
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import io.reactivex.Single

interface BusStationArrivalContract : BaseContract {

    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter {
        fun getBusStationArrivalInfo(arsId: String): Single<List<ArrivalInfoItem>>
    }
}