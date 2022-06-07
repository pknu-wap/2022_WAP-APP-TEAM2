package com.example.happysejong.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.happysejong.model.ArticleModel
import com.example.happysejong.databinding.FragmentAddArticleBinding
import com.example.happysejong.utils.DBKeys
import com.example.happysejong.utils.DBKeys.Companion.DB_ARTICLES
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddArticleFragment : Fragment() {

    private val binding by lazy{ FragmentAddArticleBinding.inflate(layoutInflater)}


    private val articleDB : DatabaseReference by lazy {
        Firebase.database.reference.child(DB_ARTICLES).child(auth.currentUser!!.uid)
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        auth = FirebaseAuth.getInstance()
        initAddArticleButton()
        return binding.root
    }

    private fun initAddArticleButton(){
        binding.addArticleButton.setOnClickListener {
            val title = binding.articleTitleTextView.text.toString()
            val content = binding.articleTextTextView.text.toString()

            if (title.isNotEmpty() && content.isNotEmpty()){
                val model = ArticleModel(auth.uid.toString(), title, content, System.currentTimeMillis())
                articleDB.setValue(model)
                val direction : NavDirections = AddArticleFragmentDirections.actionAddArticleFragmentToHomeFragment5()
                findNavController().navigate(direction)
            }
        }
    }
}