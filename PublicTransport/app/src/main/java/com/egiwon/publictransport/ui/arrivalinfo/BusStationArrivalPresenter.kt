package com.egiwon.publictransport.ui.arrivalinfo

import com.egiwon.publictransport.R
import com.egiwon.publictransport.base.BasePresenter
import com.egiwon.publictransport.data.BusServiceRepository
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import com.egiwon.publictransport.data.response.BusStationRouteInfoItem
import com.egiwon.publictransport.data.response.mapperToArrivalViewObject
import com.egiwon.publictransport.data.response.mapperToArrivalViewObjectFromParameters
import com.egiwon.publictransport.ui.arrivalinfo.vo.ArrivalViewObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class BusStationArrivalPresenter(
    private val view: BusStationArrivalContract.View,
    private val repository: BusServiceRepository
) : BasePresenter<ArrivalInfoItem>(), BusStationArrivalContract.Presenter {

    private val arrivalInfoList = mutableListOf<ArrivalInfoItem>()

    override fun getBusStationArrivalInfo(arsId: String) {
        repository.getBusStationArrivalInfo(arsId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .subscribe({
                arrivalInfoList.setItems(it) { resultList ->
                    getBusRouteInfo(resultList, arsId)
                }
            }, {
                view.showLoadFail(it)
            })
            .addTo(compositeDisposable)
    }

    private fun getBusRouteInfo(
        arrivalInfoList: List<ArrivalInfoItem>,
        arsId: String
    ) =
        repository.getBusRouteInfo(arsId)
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { view.hideLoading() }
            .subscribe({ routeInfoList ->
                val resultList = convertArrivalViewObjects(arrivalInfoList, routeInfoList)
                view.showBusStationArrivalInfo(resultList)
            }, {
                view.showLoadFail(it)
            })
            .addTo(compositeDisposable)

    private fun convertArrivalViewObjects(
        arrivalInfoList: List<ArrivalInfoItem>,
        routeInfoList: List<BusStationRouteInfoItem>
    ): List<ArrivalViewObject> =
        arrivalInfoList.map { arrivalItem ->
            val busRouteType =
                routeInfoList.find { arrivalItem.busRouteId == it.busRouteId }?.busRouteType

            val routeColor = getRouteTypeColor(busRouteType ?: 0)
            arrivalItem.mapperToArrivalViewObjectFromParameters(routeTypeColor = routeColor)
        }


    private fun getRouteTypeColor(routeType: Int): Int =
        when (routeType) {
            1 -> R.color.airport
            2 -> R.color.village
            3 -> R.color.gansun
            4 -> R.color.jisun
            5 -> R.color.circle
            6 -> R.color.wide
            7 -> R.color.incheon
            8 -> R.color.gyunggi
            else -> android.R.color.black
        }

    override fun addFavoriteBusStation(arsId: String) {
        repository.getFavoriteBusStations()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                list.takeIf { arrivalInfoList.isNotEmpty() }
                    ?.none { arsId == it.arsId }
                    ?.let { isExist ->
                        if (isExist) {
                            addFavoriteBusStationFindArrivalBusStations(arsId, list)
                        }
                    }
            }
            .addTo(compositeDisposable)
    }

    private fun addFavoriteBusStationFindArrivalBusStations(
        arsId: String,
        list: List<BusStation>
    ) {
        arrivalInfoList
            .takeIf(MutableList<ArrivalInfoItem>::isNotEmpty)
            ?.find { arsId == it.arsId }
            ?.let { addFavoriteBusStation(it, list.getLastBusStationId()) }

    }

    private fun List<BusStation>.getLastBusStationId(): Int = when (isNotEmpty()) {
        true -> this[this.size - 1].id + 1
        false -> 0
    }

    private fun addFavoriteBusStation(arrivalInfoItem: ArrivalInfoItem, id: Int) =
        repository.addFavoriteBusStation(
            BusStation(id, arrivalInfoItem.arsId, arrivalInfoItem.stNm, "")
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.showResultAddFavoriteBusStation(arrivalInfoItem.mapperToArrivalViewObject())
            }
            .addTo(compositeDisposable)

    override fun checkIfAddedFavoriteBusStation(arsId: String) {
        repository.getFavoriteBusStations()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resultList ->
                resultList
                    .filter { it.arsId == arsId }
                    .takeIf(List<BusStation>::isNotEmpty)
                    ?.let { view.hideFavoriteButton() }
            }, { view.showLoadFail(it) })
            .addTo(compositeDisposable)
    }
}