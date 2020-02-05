package com.egiwon.publictransport.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.egiwon.publictransport.data.local.model.FavoriteBusStation

@Database(
    entities = [FavoriteBusStation::class],
    version = 1,
    exportSchema = false
)
abstract class FavoriteBusStationDatabase : RoomDatabase() {
    abstract fun favoriteBusStationDao(): FavoriteBusStationDao

    companion object {
        private var instance: FavoriteBusStationDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): FavoriteBusStationDatabase {
            synchronized(lock) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteBusStationDatabase::class.java, "FavoriteBusStation.db"
                    ).build()
                }

                return instance!!
            }
        }

    }
}