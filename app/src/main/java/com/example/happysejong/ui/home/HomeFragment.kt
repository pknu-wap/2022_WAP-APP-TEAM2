package com.example.happysejong.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.happysejong.R
import com.example.happysejong.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val binding by lazy{ FragmentHomeBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding.goAddArticleButton.setOnClickListener{
            val directions : NavDirections = HomeFragmentDirections.actionHomeFragment5ToAddArticleFragment()
            findNavController().navigate(directions)
        }

        return binding.root
    }
}