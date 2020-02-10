package com.egiwon.publictransport.data.local.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BusListTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun toList(data: String?): List<BusStation> = when (data) {
        null -> emptyList()
        else -> gson.fromJson<List<BusStation>>(
            data,
            object : TypeToken<List<BusStation>>() {}.type
        )
    }

    @TypeConverter
    fun toString(busStations: List<BusStation>): String {
        return gson.toJson(busStations)
    }
}