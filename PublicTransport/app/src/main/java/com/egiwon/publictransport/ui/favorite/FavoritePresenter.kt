package com.egiwon.publictransport.ui.favorite

import com.egiwon.publictransport.base.BasePresenter
import com.egiwon.publictransport.data.BusServiceRepository
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.data.response.Item
import io.reactivex.android.schedulers.AndroidSchedulers

class FavoritePresenter(
    private val view: FavoriteContract.View,
    private val repository: BusServiceRepository
) : FavoriteContract.Presenter, BasePresenter<Item>() {

    override fun requestFavoriteStationList() {
        repository.getFavoriteBusStations()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                view.showFavoriteStationList(list)
            }.addDisposable()
    }

    override fun deleteFavoriteStation(busStation: BusStation) {
        repository.deleteFavoriteBusStation(busStation)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.refreshFavoriteAdapterList()
            }
            .addDisposable()
    }

}