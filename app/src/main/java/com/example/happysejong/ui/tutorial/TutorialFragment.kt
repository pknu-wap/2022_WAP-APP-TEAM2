package com.example.happysejong.ui.tutorial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.happysejong.R
import com.example.happysejong.databinding.FragmentTutorialBinding
import com.example.happysejong.ui.users.LoginActivity
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator

class TutorialFragment : Fragment() {

    private val binding by lazy{ FragmentTutorialBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = PagerFragmentStateAdapter(requireActivity())
        // 3개의 Fragment Add
        pagerAdapter.addFragment(Tutorial1Fragment())
        pagerAdapter.addFragment(Tutorial2Fragment())
        pagerAdapter.addFragment(Tutorial3Fragment())
        pagerAdapter.addFragment(Tutorial4Fragment())
        // Adapter
        val viewpager: ViewPager2 = view.findViewById(R.id.viewPager)
        viewpager.adapter = pagerAdapter

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.e("TutorialFragment", "Page ${position+1}")
            }
        })

        //skip button
        val btnClose: TextView =view.findViewById(R.id.btn_close)
        btnClose.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }

        val springDotsIndicator = view.findViewById<SpringDotsIndicator>(R.id.spring_dots_indicator)
        springDotsIndicator.attachTo(viewpager)
    }
}