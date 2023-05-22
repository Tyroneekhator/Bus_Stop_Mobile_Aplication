package com.example.qwergdonojsiojdo

import androidx.room.*


// database object
@Dao
interface IntrestsDao{


    // Get all restaurants of the database
    @Query("SELECT * FROM intrests")
    fun getAllIntrests(): List<Intrests>


    // Get a restaurant o restaurants by name
    @Query("SELECT * FROM intrests WHERE name=:name")
    fun getIntrestByName(name: String): Intrests?


    // Get a restaurant by id
    @Query("SELECT * FROM intrests WHERE id=:id")
    fun getIntrestById(id: Int): Intrests


    // Get restaurant by cuisine
    @Query("SELECT * FROM intrests WHERE type=:type")
    fun getIntrestsByType(type: String): List<Intrests>


    // insert a restaurant into the database
    @Insert
    fun insertNewIntrest(intrests: Intrests) : Long


}