package com.example.tyroneekhator

import androidx.room.*


// database object
@Dao
interface IntrestsDao{


    // Get all bustops by the database
    @Query("SELECT * FROM intrests")
    fun getAllIntrests(): List<Intrests>


    // Get  bustops by number
    @Query("SELECT * FROM intrests WHERE number=:name")
    fun getIntrestByName(name: String): Intrests?



    // Get a restaurant by id
    @Query("SELECT * FROM intrests WHERE id=:id")
    fun getIntrestById(id: Int): Intrests


    // Get restaurant by bus_company
    @Query("SELECT * FROM intrests WHERE bus_company=:type")
    fun getIntrestsByType(type: String): List<Intrests>


    // insert a bus stop into the database
    @Insert
    fun insertNewIntrest(intrests: Intrests) : Long


}