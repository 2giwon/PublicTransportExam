package com.egiwon.publictransport.data.local

import com.egiwon.publictransport.data.local.model.FavoriteBusStation
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import com.egiwon.publictransport.data.response.Item
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class BusServiceLocalDataSourceImpl(
    private val favoriteDao: FavoriteBusStationDao
) : BusServiceLocalDataSource {
    override fun getFavoriteBusStations(): Single<List<Item>> =
        favoriteDao.getFavoriteBusStations()
            .onErrorReturn { emptyList() }
            .map { favoriteBusStations ->
                favoriteBusStations.map { Item(arsId = it.arsId, stNm = it.name) }
            }
            .toSingle()
            .subscribeOn(Schedulers.io())

    override fun getFavoriteBusStationByArsId(arsId: String): Single<Item> =
        favoriteDao.getFavoriteBusStationByArsId(arsId)
            .onErrorReturn { FavoriteBusStation("", "") }
            .map { Item(arsId = it.arsId, stNm = it.name) }
            .toSingle()
            .subscribeOn(Schedulers.io())

    override fun insertFavoriteBusStation(busStation: ArrivalInfoItem): Completable =
        favoriteDao.insertFavoriteBusStation(
            FavoriteBusStation(
                arsId = busStation.arsId,
                name = busStation.stNm
            )
        )

    override fun deleteFavoriteBusStation(busStation: ArrivalInfoItem): Completable =
        favoriteDao.deleteFavoriteBusStation(
            FavoriteBusStation(
                arsId = busStation.arsId,
                name = busStation.stNm
            )
        )

    companion object {
        private var instance: BusServiceLocalDataSource? = null

        fun getInstance(favoriteDao: FavoriteBusStationDao): BusServiceLocalDataSource =
            instance ?: BusServiceLocalDataSourceImpl(favoriteDao).apply {
                instance = this
            }

    }

}