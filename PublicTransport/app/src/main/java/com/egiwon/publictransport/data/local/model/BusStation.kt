package com.egiwon.publictransport.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "busstations")
data class BusStation(
    @PrimaryKey val arsId: String = "",
    val stationName: String = "",
    val tag: Int,
    val createTime: Long
) {

    companion object {
        fun empty(): BusStation = BusStation("", "", -1, System.currentTimeMillis())
    }

}