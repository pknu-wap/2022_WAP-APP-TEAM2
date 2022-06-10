package com.example.happysejong.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happysejong.R
import com.example.happysejong.adapter.ArticleAdapter
import com.example.happysejong.adapter.CategoryListAdapter
import com.example.happysejong.databinding.FragmentCategoryListBinding
import com.example.happysejong.model.ArticleModel
import com.example.happysejong.ui.home.HomeFragmentDirections
import com.example.happysejong.utils.DBKeys
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CategoryListFragment : Fragment() {

    private val binding by lazy { FragmentCategoryListBinding.inflate(layoutInflater)}

    private lateinit var categoryAdapter: CategoryListAdapter
    private val categoryList = mutableListOf<ArticleModel>()

    private val categoryDB : DatabaseReference by lazy{
        Firebase.database.reference.child(DBKeys.DB_CATEGORY)
    }

    private val listener = object: ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

            val articleModel = snapshot.getValue(ArticleModel::class.java)
            articleModel ?: return

            categoryList.add(articleModel)
            categoryAdapter.submitList(categoryList)
        }
        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onChildRemoved(snapshot: DataSnapshot) {}
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onCancelled(error: DatabaseError) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val args : CategoryListFragmentArgs by navArgs()
        val category  = args.category

        connectAdapter2ArticleRecyclerView(category)

        return binding.root
    }
    private fun connectAdapter2ArticleRecyclerView(category : String){
        categoryList.clear()
        categoryAdapter = CategoryListAdapter()

        binding.categoryList.layoutManager = LinearLayoutManager(context)
        binding.categoryList.adapter = categoryAdapter
        categoryDB.child(category).addChildEventListener(listener)
    }
}