package com.egiwon.publictransport.data.local

import androidx.room.*
import com.egiwon.publictransport.data.local.model.BusStation
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface BusStationDao {

    @Query("SELECT * FROM busstations")
    fun getFavoriteBusStations(): Maybe<List<BusStation>>

    @Query("DELETE FROM busstations")
    fun deleteAll(): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBusStations(busStations: List<BusStation>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBusStation(busStation: BusStation): Completable

    @Delete
    fun deleteBusStations(busStation: BusStation): Completable
}