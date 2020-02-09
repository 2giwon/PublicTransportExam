package com.egiwon.publictransport.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.egiwon.publictransport.data.local.model.BusListTypeConverter
import com.egiwon.publictransport.data.local.model.BusStations

@Database(
    entities = [BusStations::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(BusListTypeConverter::class)
abstract class BusStationDatabase : RoomDatabase() {
    abstract fun busStationDao(): BusStationDao

    companion object {
        private var instance: BusStationDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): BusStationDatabase {
            synchronized(lock) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BusStationDatabase::class.java, "BusStation.db"
                    ).build()
                }

                return instance!!
            }
        }

    }
}