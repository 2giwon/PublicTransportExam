package com.egiwon.publictransport.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "busstations")
data class BusStation(
    @PrimaryKey val arsId: String = "",
    val stationName: String = ""
)