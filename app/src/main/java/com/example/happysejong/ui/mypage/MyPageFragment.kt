package com.example.happysejong.ui.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.happysejong.R
import com.example.happysejong.databinding.FragmentMyPageBinding
import com.example.happysejong.ui.users.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class MyPageFragment : Fragment() {

    private val binding by lazy{ FragmentMyPageBinding.inflate(layoutInflater)}
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        auth = FirebaseAuth.getInstance()

        binding.signOutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}