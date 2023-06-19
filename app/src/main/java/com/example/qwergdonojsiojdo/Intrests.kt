package com.example.tyroneekhator

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.*

// data entities
@Entity(tableName = "intrests")
data class Intrests(
    @PrimaryKey(autoGenerate = true) val id : Long,
    @ColumnInfo(name = "number")
    val name: String,
    @ColumnInfo(name = "bus_company")
    val type: String,
    @ColumnInfo(name = "final_destination")
    val descritpion: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo( name = "longitude")
    val longitude: Double
)