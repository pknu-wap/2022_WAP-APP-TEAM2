package com.example.happysejong.ui.users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.happysejong.R
import com.example.happysejong.databinding.ActivityLoginBinding
import com.example.happysejong.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private val binding by lazy{ ActivityLoginBinding.inflate(layoutInflater)}
    private lateinit var navController: NavController
    private var auth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.login_nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

    }
    override fun onStart(){
        super.onStart()
        if(auth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}