package com.egiwon.publictransport.ui.busstation

import com.egiwon.publictransport.base.BasePresenter
import com.egiwon.publictransport.data.BusServiceRepository
import com.egiwon.publictransport.data.response.Item
import io.reactivex.android.schedulers.AndroidSchedulers

class BusStationPresenter(
    private val view: BusStationContract.View,
    private val repository: BusServiceRepository
) : BasePresenter(), BusStationContract.Presenter {

    private val stationList = mutableListOf<Item>()

    override fun requestBusStations(stationName: String) {
        if (stationName.isBlank()) {
            view.showErrorSearchNameEmpty()
        } else {
            repository.getStationInfo(stationName = stationName)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { view.showLoading() }
                .doAfterTerminate { view.hideLoading() }
                .subscribe({
                    if (it.isNullOrEmpty()) {
                        view.showErrorResultEmpty()
                    } else {
                        stationList.setItems(it)
                        view.showSearchBusStationResult(stationList)
                    }
                }, { view.showErrorLoadBusStationFail() })
                .addDisposable()
        }

    }

    override fun requestFindBusStationByArsId(arsId: String) {
        if (stationList.isNotEmpty()) {
            stationList.find {
                arsId == it.arsId
            }?.let {
                view.sendFavouriteBusStation(it)
            }
        }
    }

    private fun MutableList<Item>.setItems(items: List<Item>) {
        clear()
        addAll(items)
    }
}