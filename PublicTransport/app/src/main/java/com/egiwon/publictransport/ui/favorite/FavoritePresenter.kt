package com.egiwon.publictransport.ui.favorite

import com.egiwon.publictransport.base.BasePresenter
import com.egiwon.publictransport.data.BusServiceRepository
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.data.response.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class FavoritePresenter(
    private val view: FavoriteContract.View,
    private val repository: BusServiceRepository
) : FavoriteContract.Presenter, BasePresenter<Item>() {

    private var deletedBusStation: BusStation = BusStation.empty()

    private var deletedBusPosition: Int = 0

    override fun requestFavoriteStationList() {
        repository.getFavoriteBusStations()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { list -> view.showFavoriteStationList(list) }
            .addTo(compositeDisposable)
    }

    override fun restoreDeletedFavoriteStation() {
        repository.addFavoriteBusStation(deletedBusStation)
            .andThen(repository.getFavoriteBusStations())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                view.showFavoriteStationList(it)
            }
            .addTo(compositeDisposable)
    }

    private fun showResultRestoreFavoriteBusStation(list: List<BusStation>) {
        val restoredBusList = list.toMutableList().apply {
            add(deletedBusPosition, deletedBusStation)
        }

    }

    override fun deleteFavoriteStation(busStation: BusStation, position: Int) {
        deletedBusStation = busStation
        deletedBusPosition = position

        repository.deleteFavoriteBusStation(deletedBusStation)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { view.refreshFavoriteAdapterList() }
            .addTo(compositeDisposable)
    }

    override fun updateFavoriteStationListFromTo(subList: List<BusStation>) {
        repository.updateFavoriteBusStations(getSubListSortById(subList))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }

    private fun getSubListSortById(subList: List<BusStation>): List<BusStation> =
        subList
            .asSequence()
            .sortedBy { busStation -> busStation.id }
            .mapIndexed { index, bus ->
                BusStation(
                    bus.id,
                    subList[index].arsId,
                    subList[index].stationName,
                    subList[index].tag
                )
            }
            .toList()

    override fun setFavoriteStationTag(id: Int, tag: String) {
        repository.getFavoriteBusStation(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { saveFavoriteBusStation(BusStation(id, it.arsId, it.stationName, tag)) }
            .addTo(compositeDisposable)
    }

    private fun saveFavoriteBusStation(busStation: BusStation) =
        repository.saveBusStation(busStation)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { requestFavoriteStationList() }
            .addTo(compositeDisposable)

}