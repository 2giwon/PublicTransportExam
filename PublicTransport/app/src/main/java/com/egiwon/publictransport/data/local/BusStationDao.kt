package com.egiwon.publictransport.data.local

import androidx.room.*
import com.egiwon.publictransport.data.local.model.BusStations
import io.reactivex.Maybe

@Dao
interface BusStationDao {

    @Query("SELECT * FROM busstations WHERE (stationName LIKE :stationName)")
    fun getBusStations(stationName: String): Maybe<BusStations>

    @Query("SELECT * FROM busstations ORDER BY time DESC LIMIT 1")
    fun getBusStationsCache(): Maybe<BusStations>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBusStation(busStations: BusStations)

    @Delete
    fun deleteBusStations(busStations: BusStations)
}