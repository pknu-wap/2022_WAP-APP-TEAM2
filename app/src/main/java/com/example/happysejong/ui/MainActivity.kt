package com.example.happysejong.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.happysejong.R
import com.example.happysejong.databinding.ActivityMainBinding
import com.example.happysejong.ui.home.HomeFragmentDirections
import com.example.happysejong.utils.DBKeys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy{ ActivityMainBinding.inflate(layoutInflater)}
    private lateinit var navController: NavController

    private val articleDB : DatabaseReference by lazy{
        Firebase.database.reference.child(DBKeys.DB_ARTICLES)
    }

    private var auth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemSelectedListener {  item ->
            when(item.itemId){
                R.id.chatsFragment -> {
                    duplicatedSellerUid()
                }
            }
            true
        }
    }

    private fun duplicatedSellerUid(){
        val userId = auth.currentUser!!.uid
        CoroutineScope(Dispatchers.IO).launch {
            articleDB.child(userId).get().addOnSuccessListener {
                if (it.value.toString() != "null") {
                    go2ChatScreen()
                }else{
                    Toast.makeText(this@MainActivity, "현재 생성한 방이 없습니다.", Toast.LENGTH_SHORT).show()
                    binding.bottomNavigationView.selectedItemId = R.id.homeFragment
                }
            }
        }
    }

    private fun go2ChatScreen(){
        val directions: NavDirections =
            HomeFragmentDirections.actionHomeFragment5ToChatsFragment2(auth.currentUser!!.uid)
        navController.navigate(directions)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}