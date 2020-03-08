package com.egiwon.publictransport.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.egiwon.publictransport.data.local.model.BusStation

@Database(
    entities = [BusStation::class],
    version = 2,
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
                    ).addMigrations(migration_1_2)
                        .build()
                }

                return instance!!
            }
        }

        private val migration_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE busstations ADD COLUMN tag TEXT DEFAULT '' NOT NULL")
            }

        }

    }
}