package com.egiwon.publictransport.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.egiwon.publictransport.data.response.Item

@Entity(tableName = "busstations")
data class BusStations(
    @PrimaryKey val stationName: String,
    val favorite: Boolean,
    val busStations: List<Item>,
    val time: Long
)