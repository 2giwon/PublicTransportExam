package com.egiwon.publictransport.ui.arrivalinfo

import com.egiwon.publictransport.base.BasePresenter
import com.egiwon.publictransport.data.BusServiceRepository
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import io.reactivex.android.schedulers.AndroidSchedulers

class BusStationArrivalPresenter(
    private val view: BusStationArrivalContract.View,
    private val repository: BusServiceRepository
) : BasePresenter<ArrivalInfoItem>(), BusStationArrivalContract.Presenter {

    private val arrivalInfoList = mutableListOf<ArrivalInfoItem>()

    override fun getBusStationArrivalInfo(arsId: String) {
        repository.getBusStationArrivalInfo(arsId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .doAfterTerminate { view.hideLoading() }
            .subscribe({
                arrivalInfoList.setItems(it)
                view.showBusStationArrivalInfo(arrivalInfoList)
            }, {
                view.showLoadFail(it)
            }).addDisposable()
    }

    override fun addFavoriteBusStation(arsId: String) {
        if (arrivalInfoList.isNotEmpty()) {
            arrivalInfoList.find {
                arsId == it.arsId
            }?.let {
                repository.addFavoriteBusStation(it.arsId, it.stNm)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        view.showResultAddFavoriteBusStation(it)
                    }
            }
        }
    }

    override fun checkFavoriteBusStation(arsId: String) {
        repository.getFavoriteBusStations()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.forEach { busStation ->
                    if (busStation.arsId == arsId) {
                        view.hideFavoriteButton()
                    }
                }
            }, {
                view.showLoadFail(it)
            }).addDisposable()
    }
}