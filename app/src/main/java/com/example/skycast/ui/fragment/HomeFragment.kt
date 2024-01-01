package com.example.skycast.ui.fragment

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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skycast.R
import com.example.skycast.databinding.FragmentHomeBinding
import com.example.skycast.ui.adapter.ForeCastAdapter
import com.example.skycast.ui.viewmodel.HomeViewModel
import com.example.skycast.utils.NoInternetDialogFragment
import com.example.skycast.utils.Resource
import com.example.skycast.utils.Utils
import com.example.skycast.viewmodelfactory.HomeViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
class HomeFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentHomeBinding
    private lateinit var locationCallback: LocationCallback
    private lateinit var viewModel: HomeViewModel
    private lateinit var latitude: String
    private lateinit var longitude: String
    private var isLocationUpdateReceived = false

    private val REQUEST_LOCATION_PERMISSION = 123
    private val REQUEST_GPS_SETTINGS = 456
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.progressIndicator.visibility = View.VISIBLE
        binding.foreCastList.layoutManager = LinearLayoutManager(this.context)

        val receivedData = arguments?.getString("key")
        val localizedName = arguments?.getString("localizedName")
        binding.refreshTemp.setOnClickListener {

            requestLocationPermissionAndFetchLocation()
            fetchLocationDetails()
            viewModel.getLocalLocationDetails(latitude, longitude)
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireContext())

        viewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(this.requireActivity().applicationContext)
        ).get(HomeViewModel::class.java)

        if (receivedData != null && localizedName != null) {
            binding.locTitle.text = localizedName
            viewModel.getCurrentWeather(receivedData)
            viewModel.getForcastData(receivedData)

        }

        initLocationCallback()
        requestLocationPermissionAndFetchLocation()
        fetchLocationDetails()

        return binding.root
    }

    private fun fetchLocationDetails() {

        viewModel.locationLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {

                    val locationData = resource.data

                    binding.locTitle.text = locationData.LocalizedName
                    viewModel.getCurrentWeather(locationData.Key)
                    viewModel.getForcastData(locationData.Key)
                }

                is Resource.Error -> {

                    val errorMessage = resource.message
                    if (errorMessage == "No internet connection") {

                        val dialogFragment = NoInternetDialogFragment.newInstance()
                        activity?.let {
                            dialogFragment.show(
                                it.supportFragmentManager,
                                "NoInternetDialog"
                            )
                        }
                    } else {

                        Utils().showToast(context, errorMessage)
                    }
                }

                is Resource.Loading -> {

                }
            }
        }

        viewModel.getCurrentTempLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {

                    val currentTempData = resource.data

                    binding.tempText.text =
                        currentTempData[0].Temperature.Metric.Value.toString() + " \u2103"
                    binding.realFeelTemperature.text =
                        currentTempData[0].RealFeelTemperature.Metric.Value.toString() + " \u2103"
                    binding.windSpeed.text =
                        currentTempData[0].Wind.Speed.Metric.Value.toString() + " km/h"
                    binding.uvIndex.text = currentTempData[0].UVIndex.toString()
                    binding.pressureValue.text =
                        currentTempData[0].Pressure.Metric.Value.toString() + " mb"

                    Picasso.get()
                        .load("https://developer.accuweather.com/sites/default/files/${currentTempData[0].WeatherIcon}-s.png")
                        .placeholder(R.drawable.ic_01)
                        .into(binding.tempImage)
                }

                is Resource.Error -> {

                    val errorMessage = resource.message
                    if (errorMessage == "No internet connection") {

                        val dialogFragment = NoInternetDialogFragment.newInstance()
                        activity?.let {
                            dialogFragment.show(
                                it.supportFragmentManager,
                                "NoInternetDialog"
                            )
                        }
                    } else {

                    }
                }

                is Resource.Loading -> {

                }
            }

        }

        viewModel.getForecastDetailsLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {

                    val forecastData = resource.data
                    val adapter = ForeCastAdapter(forecastData.DailyForecasts)
                    binding.foreCastList.adapter = adapter

                }

                is Resource.Error -> {

                    val errorMessage = resource.message
                    if (errorMessage == "No internet connection") {

                        val dialogFragment = NoInternetDialogFragment.newInstance()
                        activity?.let {
                            dialogFragment.show(
                                it.supportFragmentManager,
                                "NoInternetDialog"
                            )
                        }
                    } else {

                    }
                }

                is Resource.Loading -> {

                }
            }
        }
    }

    private fun initLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (!isLocationUpdateReceived) {
                    val location = locationResult.lastLocation
                    if (location != null) {
                        binding.progressIndicator.visibility = View.GONE
                        latitude = location.latitude.toString()
                        longitude = location.longitude.toString()
                        viewModel.getLocalLocationDetails(latitude, longitude)
                        isLocationUpdateReceived = true
                    }
                }
            }
        }
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
        } else {
            checkAndFetchLocation()
        }
    }

    private fun checkAndFetchLocation() {

        if (isGPSEnabled()) {
            startLocationUpdates()
        } else {
            Utils().showToast(context = context, message = "GPS is disabled. Please enable it.")
            openGPSSettings()
        }
    }

    private fun openGPSSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        activity?.startActivityForResult(intent, REQUEST_GPS_SETTINGS)
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
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    override fun onResume() {
        super.onResume()
        requestLocationPermissionAndFetchLocation()
        fetchLocationDetails()
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
                Utils().showToast(context, "Location permission denied.")
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_GPS_SETTINGS) {
            checkAndFetchLocation()
        }
    }
}