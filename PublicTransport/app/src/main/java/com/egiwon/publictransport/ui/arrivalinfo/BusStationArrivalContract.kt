package com.egiwon.publictransport.ui.arrivalinfo

import com.egiwon.publictransport.base.BaseContract
import com.egiwon.publictransport.data.response.ArrivalInfoItem

interface BusStationArrivalContract : BaseContract {

    interface View : BaseContract.View {
        fun showBusStationArrivalInfo(arrivalItems: List<ArrivalInfoItem>)

        fun showLoadFail(throwable: Throwable)
    }

    interface Presenter : BaseContract.Presenter {
        fun getBusStationArrivalInfo(arsId: String)
    }
}