package com.mobile.macs_13.com.mobile.macs_13.view.about

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mobile.macs_13.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

/*
* This class returns the view for about us page, which has a map integration in it.
* External library - osmdroid is used here to integrate the map
* Reference - https://github.com/osmdroid/osmdroid/wiki/How-to-use-the-osmdroid-library-(Java)
* */

class AboutUs : AppCompatActivity() {

    private var map: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initialising the osmdroid configuration
        val ctx: Context = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))

        //inflate and create the map
        setContentView(R.layout.activity_about_us)
        map = findViewById<View>(R.id.map) as MapView
        map!!.setTileSource(TileSourceFactory.MAPNIK)

        val mapController = map!!.controller
        mapController.setZoom(19)
        val startPoint = GeoPoint(44.6366, -63.5917)
        mapController.setCenter(startPoint)

        //Reference for marker - https://osmdroid.github.io/osmdroid/Markers,-Lines-and-Polygons.html
        val startMarker = Marker(map)
        startMarker.position = startPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        startMarker.setIcon(getResources().getDrawable(R.drawable.ic_baseline_location_on_24));
        startMarker.setTitle("Start point");
        map!!.getOverlays().add(startMarker)

    }

    override fun onResume() {
        super.onResume()
        //this will refresh the osmdroid configuration on resuming.
        map!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        //this will refresh the osmdroid configuration on resuming.
        map!!.onPause()
    }

}