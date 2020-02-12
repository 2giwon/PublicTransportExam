package com.egiwon.publictransport.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.egiwon.publictransport.data.local.model.BusStation

@Database(
    entities = [BusStation::class],
    version = 1,
    exportSchema = false
)

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