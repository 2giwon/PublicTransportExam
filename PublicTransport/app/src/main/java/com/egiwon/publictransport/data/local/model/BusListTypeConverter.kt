package com.egiwon.publictransport.data.local.model

import androidx.room.TypeConverter
import com.egiwon.publictransport.data.response.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BusListTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun toList(data: String?): List<Item> = when (data) {
        null -> emptyList()
        else -> gson.fromJson<List<Item>>(data, object : TypeToken<List<Item>>() {}.type)
    }

    @TypeConverter
    fun toString(busStations: List<Item>): String {
        return gson.toJson(busStations)
    }
}