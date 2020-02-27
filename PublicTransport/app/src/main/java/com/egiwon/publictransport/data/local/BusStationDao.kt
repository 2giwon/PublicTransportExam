package com.egiwon.publictransport.data.local

import androidx.room.*
import com.egiwon.publictransport.data.local.model.BusStation
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface BusStationDao {

    @Query("SELECT * FROM busstations ORDER BY id ASC")
    fun getFavoriteBusStations(): Maybe<List<BusStation>>

    @Query("SELECT * FROM busstations WHERE id BETWEEN :from AND :to")
    fun getFavoriteBusStationFromTo(from: Int, to: Int): Maybe<List<BusStation>>

    @Query("DELETE FROM busstations")
    fun deleteAll(): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBusStations(busStations: List<BusStation>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBusStation(busStation: BusStation): Completable

    @Delete
    fun deleteBusStations(busStation: BusStation): Completable
}