package com.example.skycast

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.skycast.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices


class HomeFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentHomeBinding
    private lateinit var locationCallback: LocationCallback

    private val REQUEST_LOCATION_PERMISSION = 123
    private val REQUEST_GPS_SETTINGS = 456


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireContext())

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult != null) {
                    val location = locationResult.lastLocation
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        showToast("Latitude: $latitude\nLongitude: $longitude")
                    }
                }
            }
        };

        requestLocationPermissionAndFetchLocation()


        return binding.root
    }

    private fun requestLocationPermissionAndFetchLocation() {
        if (ContextCompat.checkSelfPermission(
                this.requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
        else {
            checkAndFetchLocation()
        }
    }

    private fun checkAndFetchLocation() {

        if (isGPSEnabled()) {
            startLocationUpdates()
        } else {
            showToast("GPS is disabled. Please enable it.")
            openGPSSettings()
        }
    }

    private fun openGPSSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        activity?.startActivityForResult(intent, REQUEST_GPS_SETTINGS)
    }

    private fun showToast(s: String) {
        Toast.makeText(this.requireContext(), s, Toast.LENGTH_LONG).show()

    }

    private fun startLocationUpdates() {
        val locationRequest =
            LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000).setFastestInterval(5000)

        if (ActivityCompat.checkSelfPermission(
                this.requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        } else {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback!!, null)
        }
    }

    override fun onResume() {
        super.onResume()
        requestLocationPermissionAndFetchLocation()
    }

    private fun isGPSEnabled(): Boolean {
        val locationManager =
            requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager?
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)


    }


    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkAndFetchLocation()
            } else {
                    showToast("Location permission denied.")
                }
            }
        }



    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_GPS_SETTINGS) {
            checkAndFetchLocation()
        }
    }


}