package com.egiwon.publictransport.ui.arrivalinfo

import com.egiwon.publictransport.base.BaseContract
import com.egiwon.publictransport.ui.arrivalinfo.vo.ArrivalViewObject

interface BusStationArrivalContract : BaseContract {

    interface View : BaseContract.View {
        fun showBusStationArrivalInfo(arrivalItems: List<ArrivalViewObject>)

        fun showLoadFail(throwable: Throwable)

        fun showResultAddFavoriteBusStation(station: ArrivalViewObject)

        fun showFavoriteButton()

        fun hideFavoriteButton()
    }

    interface Presenter : BaseContract.Presenter {
        fun getBusStationArrivalInfo(arsId: String)

        fun addFavoriteBusStation(arsId: String)

        fun checkFavoriteBusStation(arsId: String)
    }
}