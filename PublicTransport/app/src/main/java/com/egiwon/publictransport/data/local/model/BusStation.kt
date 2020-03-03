package com.egiwon.publictransport.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "busstations")
data class BusStation(
    @PrimaryKey val id: Int,
    val arsId: String = "",
    val stationName: String = "",
    val tag: String
) {

    companion object {
        fun empty(): BusStation = BusStation(0, "", "", "")
    }

}