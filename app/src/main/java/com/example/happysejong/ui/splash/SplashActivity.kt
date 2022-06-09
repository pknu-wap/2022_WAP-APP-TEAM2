package com.example.happysejong.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.happysejong.R
import com.example.happysejong.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private val binding by lazy{ ActivitySplashBinding.inflate(layoutInflater)}
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.splash_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }
}