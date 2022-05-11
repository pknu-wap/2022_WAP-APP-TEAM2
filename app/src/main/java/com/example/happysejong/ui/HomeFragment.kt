package com.example.happysejong.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.happysejong.R
import com.example.happysejong.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val binding by lazy{ FragmentHomeBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}