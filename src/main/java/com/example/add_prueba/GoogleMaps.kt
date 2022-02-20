package com.example.add_prueba

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class GoogleMaps : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener{
    private lateinit var map: GoogleMap

    private val nuevaPoly = PolylineOptions()

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)
        createFragment()
    }

    private fun createFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
        createPolylines()

        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)

        enabledLocationPermission()

        map.setOnMapLongClickListener {

            val markerOptions = MarkerOptions().position(it)
            map.addMarker(markerOptions)
            crearPolyline(it)

        }

    }

    private fun crearPolyline(position: LatLng) {

        nuevaPoly.add(position)
        map.addPolyline(nuevaPoly)

    }

    private fun createPolylines(){
        val polylineOptions = PolylineOptions()
            .add(LatLng(39.42279021693607, -0.4195496785795037))
            .add(LatLng(39.42279021693607, -0.41673172958817695))
            .add(LatLng(39.42443888735403, -0.416893882173992))
            .add(LatLng(39.42444227267412, -0.41744169496390776))
            .add(LatLng(39.423717810429764, -0.4174022524430338))
            .add(LatLng(39.42372796649458, -0.41789747520511766))
            .add(LatLng(39.42431024506926, -0.41794568273063026))
            .add(LatLng(39.424323786373584, -0.4183970804695209))
            .add(LatLng(39.42373812255791, -0.41842337548343683))
            .add(LatLng(39.42376859073906, -0.41906322082205844))
            .add(LatLng(39.42445306610273, -0.41905478235840193))
            .add(LatLng(39.42445607507135, -0.41953531938024413))
            .add(LatLng(39.422826947491814, -0.4195395534959646))
            .width(30f)
            .color(ContextCompat.getColor(this, R.color.kotlin))

        val polyline = map.addPolyline(polylineOptions)
        polyline.isClickable = true

    }

    private fun createMarker(){
        val coordinates = LatLng(39.42308568918577, -0.41537311539917826)
        val marker = MarkerOptions().position(coordinates).title("IES La Senia")
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates,18f),
            4000,
            null
        )
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enabledLocationPermission() {
        if (!::map.isInitialized) return
        if (isLocationPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }


    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(this, "Dirigete a ajustes y acepta los permisos", Toast.LENGTH_SHORT)
                .show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                map.isMyLocationEnabled = true
            } else {
                Toast.makeText(
                    this,
                    "Para activar la localizacion ves a ajustes y acepta los permisos",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {}
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::map.isInitialized) return
        if(!!isLocationPermissionGranted()){
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            map.isMyLocationEnabled = false
            Toast.makeText(this, "Para activar la localización dirigente a ajustes del dispositivo y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "boton pulsado", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this, "estas en ${p0.latitude}, ${p0.longitude}", Toast.LENGTH_SHORT).show()
    }
}

