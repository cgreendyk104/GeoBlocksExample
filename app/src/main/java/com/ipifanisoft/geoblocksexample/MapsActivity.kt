package com.ipifanisoft.geoblocksexample

import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.ipifanisoft.geoblocksexample.data.GeoPoint
import com.ipifanisoft.geoblocksexample.data.GeoPointViewModel
import com.ipifanisoft.geoblocksexample.util.GEOBlockManager
import kotlin.math.abs

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var geoBlockManager: GEOBlockManager
    private lateinit var geoPointViewModel: GeoPointViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        geoBlockManager = GEOBlockManager()
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener {
            checkIfGeoPointShouldBeAdded(it)
        }
        geoPointViewModel = ViewModelProvider(this).get(GeoPointViewModel::class.java)
        geoPointViewModel.allGeoPoints.observe(this, Observer { lists ->
            mMap.clear()
            setGeoBlockInfo(ArrayList(lists))
        })
    }

    private fun setGeoBlockInfo(geoPoints: ArrayList<GeoPoint>) {
        val blockValues = HashMap<String, Int>()
        var maxPointsInView = 0
        geoPoints.forEach { point ->
            if(blockValues.containsKey(point.blockId)) {
                blockValues[point.blockId] = blockValues[point.blockId]!! + 1
            } else {
                blockValues[point.blockId] = 1
            }
            if(blockValues[point.blockId]!! > maxPointsInView) {
                maxPointsInView = blockValues[point.blockId]!!
            }
        }
        for((key, value) in blockValues) {
            val transparency = value.toFloat() / maxPointsInView.toFloat()
            addPolygonToMap(key, transparency)
        }
    }

    private fun checkIfGeoPointShouldBeAdded(clickPos: LatLng) {
        val blockId = geoBlockManager.getBlockIdAtGeoPoint(clickPos.latitude, clickPos.longitude)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("GeoBlock $blockId")
        builder.setMessage(getString(R.string.dialog_title_add_geopoint) + "${clickPos.longitude} - ${clickPos.longitude}")
        builder.setPositiveButton("Yep") { _, _ ->
            geoPointViewModel.insert(GeoPoint(0, blockId, clickPos.latitude, clickPos.longitude))
        }
        builder.setNegativeButton("Nope") { _, _  -> }
        builder.show()
    }

    private fun addPolygonToMap(blockId: String, transparency: Float) {
        val blockCoordinates = geoBlockManager.getBlockCoordinatesForGeoPoint(blockId)
        val polygon = mMap.addPolygon(PolygonOptions().addAll(blockCoordinates))
        polygon.tag = blockId
        polygon.strokeWidth = 0f
        polygon.fillColor = Color.argb(transparency * 0.8f, 1f, 69/255f, 0f)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item.itemId == R.id.lists) {
            startActivity(Intent(this, GeoPointsListActivity::class.java))
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu!!.findItem(R.id.lists)
        val icon = menuItem.icon
        icon.mutate()
        icon.setTint(Color.WHITE)
        return true
    }
}



