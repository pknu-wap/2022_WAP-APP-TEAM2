package com.example.happysejong.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.happysejong.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment(), View.OnClickListener{
    private val binding by lazy{ FragmentCategoryBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        initCategoryButton()
        return binding.root
    }

    private fun initCategoryButton() {
        binding.allButton.setOnClickListener(this)
        binding.chickenButton.setOnClickListener(this)
        binding.pizzaButton.setOnClickListener(this)
        binding.koreaButton.setOnClickListener(this)
        binding.japanButton.setOnClickListener(this)
        binding.chinaButton.setOnClickListener(this)
        binding.westButton.setOnClickListener(this)
        binding.hamburgerButton.setOnClickListener(this)
        binding.etcButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.allButton.id -> { }
            binding.chickenButton.id -> {
                val direction : NavDirections = CategoryFragmentDirections.actionCategoryFragmentToCategoryListFragment(
                    "치킨"
                )
                findNavController().navigate(direction)
            }
            binding.pizzaButton.id -> { }
            binding.koreaButton.id -> { }
            binding.japanButton.id -> { }
            binding.chinaButton.id -> { }
            binding.westButton.id -> { }
            binding.hamburgerButton.id -> { }
            binding.etcButton.id -> { }
        }
    }
}