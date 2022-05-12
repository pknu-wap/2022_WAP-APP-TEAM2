package com.example.happysejong.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.happysejong.R
import com.example.happysejong.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {

    private val binding by lazy{ FragmentMyPageBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return binding.root
    }
}