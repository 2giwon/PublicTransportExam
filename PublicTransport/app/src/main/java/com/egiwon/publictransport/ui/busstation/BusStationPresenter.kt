package com.egiwon.publictransport.ui.busstation

import com.egiwon.publictransport.base.BasePresenter
import com.egiwon.publictransport.data.BusServiceRepository
import io.reactivex.android.schedulers.AndroidSchedulers

class BusStationPresenter(
    private val view: BusStationContract.View,
    private val repository: BusServiceRepository
) : BasePresenter(), BusStationContract.Presenter {

    override fun requestBusStations(stationName: String) {
        if (stationName.isBlank()) {
            view.showErrorSearchNameEmpty()
        } else {
            repository.getStationInfo(stationName = stationName)
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { view.showLoading() }
                .doAfterTerminate { view.hideLoading() }
                .subscribe({
                    if (it.isNullOrEmpty()) {
                        view.showErrorResultEmpty()
                    } else {
                        view.showSearchBusStationResult(it)
                    }
                }, { view.showErrorLoadBusStationFail() })
                .addDisposable()
        }

    }

}