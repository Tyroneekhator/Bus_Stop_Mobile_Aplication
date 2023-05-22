package com.example.qwergdonojsiojdo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem

class MainActivity : AppCompatActivity(), LocationListener {


    // use to call the map after the oncreate is called
    lateinit var map1: MapView
    // set the variables for latitude and longitude
    var latitude = 0.0
    var longitude = 0.0
    var showingDatabaseIntrests = false;
    var upload = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        // configuration for map
        // this require to download OSM maps
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));



        // call the requestLocation permission
        requestLocation()

        //------------------------------------------------------------------------------------------

        // Fragments

        // first fragment map
        class Frag1: Fragment(R.layout.map){
            override fun onViewCreated(fragmentView: View, b: Bundle?) {
                // configuration for map

                // get the map by id
                map1 = findViewById<MapView>(R.id.map1)
                // set the zoom map
                map1.controller.setZoom(11.0)
                // set the map center to this coordenates
                map1.controller.setCenter(GeoPoint(latitude, longitude))
                map1

            }


            override fun onDestroy() {
                super.onDestroy()
            }

        }

        // second fragment
        class Frag2: Fragment(R.layout.add_intrest){
            override fun onViewCreated(fragmentView: View, b: Bundle?) {
                // get the values textfields
                // since we can get  the values will get the by id and then parsing into strings

                val editText_name = fragmentView.findViewById<EditText>(R.id.et1name)
                val editText_type = fragmentView.findViewById<EditText>(R.id.et2type)
                val editText_description = fragmentView.findViewById<EditText>(R.id.et3description)
                //get the button to add an eventlister
                val button_add = fragmentView.findViewById<Button>(R.id.btnadd1)

                // event listener when button is clicked

                button_add.setOnClickListener {
                    // parse into string

                    val name = editText_name.text.toString()
                    val type = editText_type.text.toString()
                    val description = editText_description.text.toString()

                    // handle that editext after add new intrest still have the value on it
                    //add code to handle the preference to add to web

                    // call the add poi function  and addedd the variables as strings parameters

                    // code to check preference
                    if(upload){
                        // code to add to web
                    }else{
                        // send error
                    }
                    addPOIToDatabase(name, type, description)
                }

            }
        }

        // handle fragments

        val navigation = findViewById<NavigationView>(R.id.nv)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)

        // create obj of the frag

        // map
        val fragment1 = Frag1()
        // add resturant
        val fragment2 = Frag2()

        // set up navigation

        navigation.setNavigationItemSelectedListener {
            var fragment = if (true) fragment1 else fragment2
            // to handle many fragment we use a when statement like this

            when(it.itemId){
                R.id.lookmap ->{
                    fragment = fragment1
                    getRestaurantsFromDatabase()
                    //getRestaurantsFromWebApi()
                }
                R.id.newpointsofintrest ->{
                    fragment = fragment2
                }
            }

            drawerLayout.closeDrawers()
            supportFragmentManager.commit {
                replace(R.id.frameLayout1, fragment)
            }
            true
        }

        supportFragmentManager.commit {
            replace(R.id.frameLayout1, fragment1)
        }


    }


    private fun requestLocation() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            val mgr = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,this)
        }else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION),0)
    }
    override fun onLocationChanged(newLoc: Location) {
        // Toast.makeText (this, "Location=${newLoc.latitude},${newLoc.longitude}", Toast.LENGTH_LONG).show()
        // map1.controller.setZoom(18.0)
        latitude = newLoc.latitude
        longitude = newLoc.longitude

        map1.controller.setCenter(GeoPoint(newLoc.latitude,newLoc.longitude ))
    }

    // Deprecated at API level 29, but must still be included, otherwise your
    // app will crash on lower-API devices as their API will try and call it
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

    }

    //--------------------------------------------------------------------------------------

    //menu set up

    //override the oncreateoptionmenu to add the menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.preference, menu)
        return true
    }
    //override onoptionsitemselected
    // when the item with x value id is selected then this does x
    // use for add restaurant and get the intent




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.preferences ->{
                val intent = Intent(this, preferentials::class.java)
                startActivity(intent)
                //return true
            }
        }
        return false

    }




    //----------------------------------------------------------------------------------------------

    // database stuff

    // adds pois to the database

    fun addPOIToDatabase(name: String, type: String, description: String){

        val db = IntrestsDatabase.getDatabase(application)

        val newIntrests = Intrests(0, name, type, description, latitude, longitude)
        //use lifecycle to run in a second background
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                db.IntrestsDao().insertNewIntrest(newIntrests)
            }
        }
    }

    //-----------------------------------------------------------------------------------------------

    fun getRestaurantsFromDatabase(){

        val db = IntrestsDatabase.getDatabase(application)
        showingDatabaseIntrests = true
        // use lifecycle to run in background
        // not main treat to interrup
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                // get all intrests
                val intrests = db.IntrestsDao().getAllIntrests()
                //
                val markerGestureListener = object:
                    ItemizedIconOverlay.OnItemGestureListener<OverlayItem>
                {
                    override fun onItemLongPress(i: Int, item: OverlayItem) : Boolean
                    {
                        Toast.makeText(this@MainActivity, item.snippet, Toast.LENGTH_SHORT).show()
                        return true
                    }

                    override fun onItemSingleTapUp(i: Int, item: OverlayItem): Boolean
                    {
                        Toast.makeText(this@MainActivity, item.snippet, Toast.LENGTH_SHORT).show()
                        return true
                    }
                }
                //
                val items = ItemizedIconOverlay(this@MainActivity, arrayListOf<OverlayItem>(), markerGestureListener)
                // loop through the list of restaurants
                intrests.forEach {
                    val inf =" name: ${it.name} type: ${it.type} description: ${it.descritpion}"
                    val new_res_item =OverlayItem(it.name,inf, GeoPoint(it.latitude, it.longitude))
                    items.addItem(new_res_item)
                    map1.overlays.add(items)
                }


            }
            map1.invalidate()
        }
    }
    //----------------------------------------------------------------------------------------------------------------------------



    override fun onResume() {
        super.onResume()
        val preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
        upload = preferences.getBoolean("upload", false)
    }


}

