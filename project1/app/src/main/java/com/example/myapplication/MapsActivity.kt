package com.example.myapplication

import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.myapplication.databinding.ActivityMapsBinding
import org.jetbrains.anko.doAsync

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var country: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerview)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Fake news
        val news = getFakeNews()
        val adapter = MapNewsAdapter(news)
        recyclerView.setAdapter(adapter)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

//        val newsManager = NewsManager()
//        val apiKey = getString(R.string.news_key)
//        doAsync{
//            val news: List<MapNews> = try {
//                newsManager.retrieveMapNews(country, apiKey)
//            } catch(exception: Exception) {
//                Log.e("MapsActivity", "Retrieving News failed", exception)
//                listOf<MapNews>()
//            }
//
//            runOnUiThread {
//                if (news.isNotEmpty()) {
//                    val adapter = MapNewsAdapter(news)
//                    recyclerView.setAdapter(adapter)
//                    recyclerView.layoutManager = LinearLayoutManager(this@MapsActivity, LinearLayoutManager.HORIZONTAL, false)
//                } else {
//                    Toast.makeText(this@MapsActivity,
//                        "Failed to retrieve News!",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
//        }
    }

    private fun getFakeNews(): List<MapNews> {
        return listOf(
            MapNews("ESPN","Lost Ark is getting more servers to ease player congestion", "A popular MMO is facing congestion issues – have you heard of that one before? While Final Fantasy XIV had its fair share of challenges following Endwalker’s launch, it’s now Lost Ark’s turn to grapple with swelling player counts."),
            MapNews("ESPN", "News brief: COVID vaccines for kids, Russia-Ukraine crisis, Canadian protests", "Pfizer adds data to its request to get the OK for its COVID vaccine for young kids. Biden cautions Americans in Ukraine to leave."),
            MapNews("ESPN","Deforestation in Brazil’s Amazon hits new record in January", "Brazil recorded the most deforestation ever in the Amazon rainforest for the month of January, according to new government data, as the destruction continues to worsen despite the government’s recent pledges to bring it under control.")
        )
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapLongClickListener { coords: LatLng->
            mMap.clear()

            doAsync{

                val geocoder = Geocoder(this@MapsActivity)
                val results: List<Address> = try{
                    geocoder.getFromLocation(
                        coords.latitude,
                        coords.longitude,
                        10
                    )
                } catch(exception: Exception) {
                    Log.e("MapsActivity", "Geocoding failed!", exception)
                    listOf()
                }

                runOnUiThread {
                    if (results.isNotEmpty()) {
                        val firstResult = results[0]
                        val addressLine = firstResult.getAddressLine(0)

                        country = firstResult.countryName

                        val marker = MarkerOptions()
                            .position(coords)
                            .title(addressLine)

                        mMap.addMarker(marker)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, 10.0f))
                    } else {
                        Toast.makeText(this@MapsActivity, "No results found!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}