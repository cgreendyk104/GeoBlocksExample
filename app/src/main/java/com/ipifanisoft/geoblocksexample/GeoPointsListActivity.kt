package com.ipifanisoft.geoblocksexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipifanisoft.geoblocksexample.adapter.GeoBlockActionListener
import com.ipifanisoft.geoblocksexample.adapter.GeoBlocksAdapter
import com.ipifanisoft.geoblocksexample.data.GeoPoint
import com.ipifanisoft.geoblocksexample.data.GeoPointViewModel
import kotlinx.android.synthetic.main.activity_geo_points_list.*

class GeoPointsListActivity: AppCompatActivity(), GeoBlockActionListener {

    private lateinit var geoPointViewModel: GeoPointViewModel
    private lateinit var adapter: GeoBlocksAdapter
    private var geoBlocks = ArrayList<GeoBlockListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geo_points_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rv_geo_points.layoutManager = LinearLayoutManager(this)
        geoPointViewModel = ViewModelProvider(this).get(GeoPointViewModel::class.java)
        geoPointViewModel.allGeoPoints.observe(this, Observer { lists ->
            setGeoBlockInfo(ArrayList(lists))
        })
    }

    private fun setGeoBlockInfo(geoPoints: ArrayList<GeoPoint>) {
        val blockValues = HashMap<String, Int>()
        geoBlocks.clear()
        geoPoints.forEach { point ->
            if(blockValues.containsKey(point.blockId)) {
                blockValues[point.blockId] = blockValues[point.blockId]!! + 1
            } else {
                blockValues[point.blockId] = 1
            }
        }
        for((key, value) in blockValues) {
            geoBlocks.add(GeoBlockListItem(key, value))
        }
        adapter = GeoBlocksAdapter(ArrayList(geoBlocks.sortedBy { it.count }.reversed()), this, this)
        rv_geo_points.adapter = adapter
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun removeGeoBlock(id: String) {
        geoPointViewModel.deleteGeoPointsByBlock(id)
    }
}

data class GeoBlockListItem(val key: String, val count: Int)