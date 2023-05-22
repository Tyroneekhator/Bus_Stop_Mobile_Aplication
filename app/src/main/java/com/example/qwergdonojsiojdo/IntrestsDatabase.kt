package com.example.qwergdonojsiojdo

import androidx.room.*
import android.content.Context

// database object
@Database(entities = arrayOf(Intrests::class), version = 2, exportSchema = false)
public abstract class IntrestsDatabase: RoomDatabase(){
    abstract fun IntrestsDao(): IntrestsDao
    companion object{
        @Volatile
        private var instance: IntrestsDatabase? = null

        fun getDatabase(ctx:Context): IntrestsDatabase{
            var tmpInstance = instance
            if(tmpInstance == null){
                tmpInstance = Room.databaseBuilder(
                    ctx.applicationContext,
                    IntrestsDatabase::class.java,
                    "RestaurantDatabase"
                ).fallbackToDestructiveMigration().build()
                instance = tmpInstance
            }
            return tmpInstance
        }
    }
}