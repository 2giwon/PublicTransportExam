package com.egiwon.publictransport.ui.busstation

import com.egiwon.publictransport.base.BasePresenter
import com.egiwon.publictransport.data.BusServiceRepository
import com.egiwon.publictransport.data.response.Item
import io.reactivex.android.schedulers.AndroidSchedulers

class BusStationPresenter(
    private val view: BusStationContract.View,
    private val repository: BusServiceRepository
) : BasePresenter<Item>(), BusStationContract.Presenter {

    override fun requestBusStations() {
        repository.getStationCache()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showSearchBusCache(it.busStations, it.stationName)
            }, {

            }).addDisposable()
    }

    override fun requestBusStations(stationName: String) {
        if (stationName.isBlank()) {
            view.showErrorSearchNameEmpty()
        } else {
            repository.getStationInfo(stationName = stationName)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doAfterTerminate { view.hideLoading() }
                .subscribe({
                    if (it.isNullOrEmpty()) {
                        view.showErrorResultEmpty()
                    } else {
                        view.showSearchBusStationResult(it)
                    }
                }, {
                    view.showErrorLoadBusStationFail()
                })
                .addDisposable()
        }

    }


}