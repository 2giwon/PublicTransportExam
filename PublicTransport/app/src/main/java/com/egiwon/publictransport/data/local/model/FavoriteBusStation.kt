package com.egiwon.publictransport.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritebusstations")
data class FavoriteBusStation(
    @PrimaryKey val arsId: String,
    val name: String
)