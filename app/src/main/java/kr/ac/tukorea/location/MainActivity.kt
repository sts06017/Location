package kr.ac.tukorea.location

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    lateinit var button : Button
    var longitude:Double?=null
    var latitude:Double?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById<Button>(R.id.button)
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val locationListener = object : LocationListener {
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                super.onStatusChanged(provider, status, extras)
                // provider의 상태가 변경될때마다 호출
                // deprecated
            }

            override fun onLocationChanged(location: Location) {
                // 위치 정보 전달 목적으로 호출(자동으로 호출)

                 longitude = location.longitude
                 latitude = location.latitude

                Log.d("LotLog", "Latitude : $latitude, Longitude : $longitude")
            }

            override fun onProviderEnabled(provider: String) {
                super.onProviderEnabled(provider)
                // provider가 사용 가능한 생태가 되는 순간 호출
            }

            override fun onProviderDisabled(provider: String) {
                super.onProviderDisabled(provider)
                // provider가 사용 불가능 상황이 되는 순간 호출
            }
        }
        // 매개변수로 위치 정보 제공자, LocationListener 호출 주기, 변경 위치 거리의 정보, LocationListener
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            var permissions = arrayOf( android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION )

            ActivityCompat.requestPermissions(this, permissions,100)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000
            , 10.0f, locationListener)

        button.setOnClickListener {
            button.setText("Latitude : $latitude Longitude : $longitude")
            locationManager.removeUpdates(locationListener)
        }
    }
}