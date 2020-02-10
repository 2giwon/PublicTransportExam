package com.egiwon.publictransport.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "busstations")
data class BusStations(
    @PrimaryKey val stationName: String,
    val busStations: List<BusStation>,
    val time: Long
)