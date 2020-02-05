package com.egiwon.publictransport.data.local

import androidx.room.*
import com.egiwon.publictransport.data.local.model.FavoriteBusStation
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface FavoriteBusStationDao {

    @Query("SELECT * FROM favoritebusstations")
    fun getFavoriteBusStations(): Maybe<List<FavoriteBusStation>>

    @Query("SELECT * FROM favoritebusstations WHERE (arsId LIKE :arsId)")
    fun getFavoriteBusStationByArsId(arsId: String): Maybe<FavoriteBusStation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteBusStation(busStation: FavoriteBusStation): Completable

    @Delete
    fun deleteFavoriteBusStation(busStation: FavoriteBusStation): Completable
}