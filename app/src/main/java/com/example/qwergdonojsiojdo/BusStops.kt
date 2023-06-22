package com.example.tyroneekhator

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.*

// data entities
@Entity(tableName = "busStops")
data class BusStops(
    @PrimaryKey(autoGenerate = true) val id : Long,
    @ColumnInfo(name = "number")
    val number: String,
    @ColumnInfo(name = "bus_company")
    val type: String,
    @ColumnInfo(name = "final_destination")
    val destination: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo( name = "longitude")
    val longitude: Double
)