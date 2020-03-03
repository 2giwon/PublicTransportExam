package com.egiwon.publictransport.ui.arrivalinfo

import com.egiwon.publictransport.R
import com.egiwon.publictransport.base.BasePresenter
import com.egiwon.publictransport.data.BusServiceRepository
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import com.egiwon.publictransport.data.response.BusStationRouteInfoItem
import com.egiwon.publictransport.data.response.mapperToArrivalViewObject
import com.egiwon.publictransport.ui.arrivalinfo.vo.ArrivalViewObject
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
                arrivalInfoList.setItems(it) { resultList -> getBusRouteInfo(resultList, arsId) }
            }, {
                view.showLoadFail(it)
            }).addDisposable()
    }

    private fun getBusRouteInfo(
        arrivalInfoList: List<ArrivalInfoItem>,
        arsId: String
    ) =
        repository.getBusRouteInfo(arsId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ routeInfoList ->
                val resultList = convertArrivalViewObjects(arrivalInfoList, routeInfoList)

                view.showBusStationArrivalInfo(resultList)
            }, {
                view.showLoadFail(it)
            }).addDisposable()

    private fun convertArrivalViewObjects(
        arrivalInfoList: List<ArrivalInfoItem>,
        routeInfoList: List<BusStationRouteInfoItem>
    ): List<ArrivalViewObject> =
        arrivalInfoList.map { arrivalItem ->
            val busRouteType =
                routeInfoList.find { arrivalItem.busRouteId == it.busRouteId }?.busRouteType

            val routeColor = getRouteTypeColor(busRouteType ?: 0)
            ArrivalViewObject(
                arsId = arrivalItem.arsId,
                routeName = arrivalItem.rtNm,
                stationName = arrivalItem.stNm,
                routeWay = arrivalItem.adirection,
                arrivalTime = arrivalItem.arrmsg1,
                nextBus = arrivalItem.arrmsg2,
                routeTypeColor = routeColor
            )
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
        repository.getFavoriteBusStationLastIndex()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { index ->
                if (arrivalInfoList.isNotEmpty()) {
                    arrivalInfoList
                        .find { arsId == it.arsId }
                        ?.let { addFavoriteBusStation(it, index) }
                }
            }.addDisposable()
    }

    private fun addFavoriteBusStation(arrivalInfoItem: ArrivalInfoItem, id: Int) =
        repository.addFavoriteBusStation(
            BusStation(id, arrivalInfoItem.arsId, arrivalInfoItem.stNm, "")
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.showResultAddFavoriteBusStation(arrivalInfoItem.mapperToArrivalViewObject())
            }.addDisposable()

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