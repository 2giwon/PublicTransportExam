package com.egiwon.publictransport.ui.arrivalinfo

import com.egiwon.publictransport.base.BasePresenter
import com.egiwon.publictransport.data.BusServiceRepository
import io.reactivex.android.schedulers.AndroidSchedulers

class BusStationArrivalPresenter(
    private val view: BusStationArrivalContract.View,
    private val repository: BusServiceRepository
) : BasePresenter(), BusStationArrivalContract.Presenter {

    override fun getBusStationArrivalInfo(arsId: String) {
        repository.getBusStationArrivalInfo(arsId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showBusStationArrivalInfo(it)
            }, {
                view.showLoadFail(it)
            }).addDisposable()
    }

}