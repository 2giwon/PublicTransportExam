package com.egiwon.publictransport

import com.egiwon.publictransport.base.BasePresenter
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.data.local.model.BusStations
import com.egiwon.publictransport.data.response.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class MainPresenter(
    private val view: MainContract.View
) : BasePresenter<Item>(), MainContract.Presenter {

    private val favoriteSet = mutableSetOf<BusStation>()
    private val busStationSet = mutableSetOf<BusStation>()
    private var lastSearchQuery = ""
    private var lastUpdateTime = 0L

    private val _favoriteSubject: BehaviorSubject<BusStation> = BehaviorSubject.create()
    private val favoriteSubject: BehaviorSubject<BusStation> get() = _favoriteSubject

    init {
        _favoriteSubject.subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                favoriteSet.add(it)
            }.addDisposable()
    }

    override fun requestFavoriteSubject(block: (BehaviorSubject<BusStation>) -> Unit) {
        block(favoriteSubject)
    }

    override fun requestFavoriteList(block: (List<BusStation>) -> Unit) {
        block(favoriteSet.toList())
    }

    override fun requestBusStationCache(block: (BusStations) -> Unit) {
        block(BusStations(lastSearchQuery, busStationSet.toList(), lastUpdateTime))
    }

    override fun getSearchBusStationResult(block: () -> BusStations) {
        block().run {
            busStationSet.addAll(busStations)
            lastSearchQuery = stationName
            lastUpdateTime = time
        }
    }

}