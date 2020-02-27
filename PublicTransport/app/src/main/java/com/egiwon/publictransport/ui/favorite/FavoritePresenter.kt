package com.egiwon.publictransport.ui.favorite

import com.egiwon.publictransport.base.BasePresenter
import com.egiwon.publictransport.data.BusServiceRepository
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.data.response.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class FavoritePresenter(
    private val view: FavoriteContract.View,
    private val repository: BusServiceRepository
) : FavoriteContract.Presenter, BasePresenter<Item>() {

    private var deletedBusStation: BusStation = BusStation.empty()

    private var deletedBusPosition: Int = 0

    private val updateFavoriteListSubject = PublishSubject.create<List<BusStation>>()

    init {
        updateFavoriteListSubject
            .debounce(1L, TimeUnit.SECONDS)
            .subscribe { list ->
                val newList = list.map {
                    BusStation(it.arsId, it.stationName, it.tag, System.currentTimeMillis() + 1)
                }
                updateFavoriteBusStations(newList)
            }.addDisposable()
    }

    private fun updateFavoriteBusStations(busStations: List<BusStation>) =
        repository.updateFavoriteBusStations(busStations)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addDisposable()

    override fun requestFavoriteStationList() {
        repository.getFavoriteBusStations()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list -> view.showFavoriteStationList(list) }
            .addDisposable()
    }

    override fun restoreDeletedFavoriteStation() {
        requestFavoriteStationList()
    }

    override fun deleteFavoriteStationTemporarily(busStation: BusStation, position: Int) {
        deletedBusStation = busStation
        deletedBusPosition = position

        view.refreshFavoriteAdapterList()
    }

    override fun deleteFavoriteStationPermanently() {
        repository.deleteFavoriteBusStation(deletedBusStation)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addDisposable()
    }

    override fun updateFavoriteStationList(busStations: List<BusStation>) {
        updateFavoriteListSubject.onNext(busStations)
    }

    override fun requestBusStationTag(busStationIndex: Int, tagIndex: Int) {
        repository.getFavoriteBusStations()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                val busStation = BusStation(
                    arsId = list[busStationIndex].arsId,
                    stationName = list[busStationIndex].stationName,
                    tag = tagIndex,
                    createTime = list[busStationIndex].createTime
                )

                val mutableList = list.toMutableList()
                mutableList[busStationIndex] = busStation
                setBusStationTag(mutableList[busStationIndex])
            }
            .addDisposable()

    }

    private fun setBusStationTag(busStation: BusStation) {
        repository.saveBusStation(busStation)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addDisposable()
    }

}