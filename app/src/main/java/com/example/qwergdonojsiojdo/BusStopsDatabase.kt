package com.example.tyroneekhator

import androidx.room.*
import android.content.Context

// database object
@Database(entities = arrayOf(BusStops::class), version = 4, exportSchema = false)
public abstract class BusStopsDatabase: RoomDatabase(){
    abstract fun BusStopsDao(): BusStopsDao
    companion object{
        @Volatile
        private var instance: BusStopsDatabase? = null

        fun getDatabase(ctx:Context): BusStopsDatabase{
            var tmpInstance = instance
            if(tmpInstance == null){
                tmpInstance = Room.databaseBuilder(
                    ctx.applicationContext,
                    BusStopsDatabase::class.java,
                    "BusStopsDatabase"
                ).fallbackToDestructiveMigration().build()
                instance = tmpInstance
            }
            return tmpInstance
        }
    }
}
