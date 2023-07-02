package com.example.tyroneekhator

import androidx.room.*


// database object
@Dao
interface BusStopsDao{


    // Get all bus stops by the database
    @Query("SELECT * FROM busStops")
    fun getAllbusStops(): List<BusStops>


    // Get  bus stops by number
    @Query("SELECT * FROM busStops WHERE number=:name")
    fun getbusStopByName(name: String): BusStops?



    // Get a bus stops by id
    @Query("SELECT * FROM busStops WHERE id=:id")
    fun getbusStopsById(id: Int): BusStops


    // Get bus stops by bus_company
    @Query("SELECT * FROM busStops WHERE bus_company=:type")
    fun getbusStopsByType(type: String): List<BusStops>


    // insert a bus stop into the database
    @Insert
    fun insertNewbusStop(busStops: BusStops) : Long


}