package com.example.qwergdonojsiojdo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.*

// data entities
@Entity(tableName = "intrests")
data class Intrests(
    @PrimaryKey(autoGenerate = true) val id : Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "descritpion")
    val descritpion: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo( name = "longitude")
    val longitude: Double
)