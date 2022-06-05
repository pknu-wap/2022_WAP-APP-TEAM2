package com.example.happysejong.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.happysejong.R
import com.example.happysejong.databinding.FragmentMyPageBinding
import com.example.happysejong.model.UserModel
import com.example.happysejong.ui.users.LoginActivity
import com.example.happysejong.utils.DBKeys
import com.example.happysejong.utils.DBKeys.Companion.DB_USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MyPageFragment : Fragment() {

    private val binding by lazy{ FragmentMyPageBinding.inflate(layoutInflater)}
    private lateinit var auth: FirebaseAuth
    private val currentUserDB : DatabaseReference by lazy{
        Firebase.database.reference.child(DB_USERS)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        auth = FirebaseAuth.getInstance()
        getUsersInformation()

        binding.signOutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
    private fun getUsersInformation(){
        val userId = auth.currentUser?.uid.orEmpty()
        currentUserDB.child(userId).child("dormitory").get().addOnSuccessListener {
            binding.myPageDormitoryTextView.text = it.value.toString()
        }
        currentUserDB.child(userId).child("nickName").get().addOnSuccessListener{
            binding.myPageNicknameTextView.text = it.value.toString()
        }
    }
}