package com.egiwon.publictransport.ui.arrivalinfo

import com.egiwon.publictransport.base.BasePresenter
import com.egiwon.publictransport.data.BusServiceRepository
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import io.reactivex.android.schedulers.AndroidSchedulers

class BusStationArrivalPresenter(
    private val view: BusStationArrivalContract.View,
    private val repository: BusServiceRepository
) : BasePresenter(), BusStationArrivalContract.Presenter {

    private val arrivalInfoList = mutableListOf<ArrivalInfoItem>()

    override fun getBusStationArrivalInfo(arsId: String) {
        repository.getBusStationArrivalInfo(arsId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                arrivalInfoList.setItems(it)
                view.showBusStationArrivalInfo(arrivalInfoList)
            }, {
                view.showLoadFail(it)
            }).addDisposable()
    }

    private fun MutableList<ArrivalInfoItem>.setItems(items: List<ArrivalInfoItem>) {
        clear()
        addAll(items)
    }

}