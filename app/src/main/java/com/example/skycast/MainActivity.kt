package com.example.skycast


import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.skycast.databinding.ActivityMainBinding
import com.example.skycast.ui.fragment.HomeFragment
import com.example.skycast.ui.fragment.SearchFragment
import com.example.skycast.ui.fragment.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        bottomNavigationView= binding.bottomNavigation
        setContentView(binding.root)
        supportActionBar?.hide()
        openFragment(HomeFragment())

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Load home fragment or perform corresponding action
                    openFragment(HomeFragment())
                    true
                }

                R.id.navigation_search -> {
                    openFragment(SearchFragment())// Load dashboard fragment or perform corresponding action
                    true
                }

                R.id.navigation_settings -> {
                    openFragment(SettingsFragment())//
                    true
                }

                else -> false

            }
        }




    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentView, fragment)
            .commit()

    }


}
