package com.example.happysejong.ui.tutorial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.happysejong.R
import com.example.happysejong.databinding.FragmentSplashBinding
import com.example.happysejong.ui.users.LoginActivity

class SplashFragment : Fragment() {

    private val binding by lazy { FragmentSplashBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            if(isTutorialFinished()) {
                startActivity(Intent(context, LoginActivity::class.java))
                activity?.finish()
            }
            else {
                findNavController().navigate(R.id.action_splashFragment_to_tutorialFragment)
            }
        }, 1500)
    }

    private fun isTutorialFinished(): Boolean {
        val prefs = requireActivity().getSharedPreferences("tutorial", Context.MODE_PRIVATE)
        return prefs.getBoolean("finished", false)
    }

}