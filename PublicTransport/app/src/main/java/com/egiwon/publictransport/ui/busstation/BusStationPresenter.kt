package com.egiwon.publictransport.ui.busstation

import com.egiwon.publictransport.base.BasePresenter
import com.egiwon.publictransport.data.BusServiceRepository
import com.egiwon.publictransport.data.response.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class BusStationPresenter(
    private val view: BusStationContract.View,
    private val repository: BusServiceRepository
) : BasePresenter<Item>(), BusStationContract.Presenter {

    override fun requestBusStations(stationName: String) {
        repository.getStationInfo(stationName = stationName)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .doAfterTerminate { view.hideLoading() }
            .subscribeBy(
                onSuccess = { busStations ->
                    runCatching { busStations.busStations.isNotEmpty() }
                        .onFailure { view.showErrorResultEmpty() }
                        .onSuccess { view.showSearchBusStationResult(busStations) }
                },
                onError = {
                    view.showErrorLoadBusStationFail()
                }
            )
            .addTo(compositeDisposable)
    }




}