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

    private var deletedBusStation: BusStation = BusStation.empty()

    private var deletedBusPosition: Int = 0

    private var lastId = 0

    override fun requestFavoriteStationList() {
        repository.getFavoriteBusStations()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                view.showFavoriteStationList(list)
                lastId = list.size
            }
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

    override fun updateFavoriteStationListFromTo(subList: List<BusStation>) {
        val mutableList = subList.toMutableList()
        mutableList.sortBy { it.id }

        val updateList = mutableList.mapIndexed { index, bus ->
            BusStation(bus.id, subList[index].arsId, subList[index].stationName, subList[index].tag)
        }

        repository.updateFavoriteBusStations(updateList)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addDisposable()
    }


    override fun requestBusStationTag(busStationIndex: Int, tagIndex: Int) {
        repository.getFavoriteBusStations()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                val busStation = BusStation(
                    id = list[busStationIndex].id,
                    arsId = list[busStationIndex].arsId,
                    stationName = list[busStationIndex].stationName,
                    tag = ""
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