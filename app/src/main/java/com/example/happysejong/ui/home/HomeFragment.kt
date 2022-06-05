package com.example.happysejong.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happysejong.R
import com.example.happysejong.adapter.ArticleAdapter
import com.example.happysejong.databinding.FragmentHomeBinding
import com.example.happysejong.model.ArticleModel
import com.example.happysejong.utils.DBKeys
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private val binding by lazy{ FragmentHomeBinding.inflate(layoutInflater)}
    private lateinit var auth: FirebaseAuth

    private val userDB : DatabaseReference by lazy{
        Firebase.database.reference.child(DBKeys.DB_USERS)
    }
    private val articleDB : DatabaseReference by lazy{
        Firebase.database.reference.child(DBKeys.DB_ARTICLES)
    }

    private lateinit var articleAdapter: ArticleAdapter
    private val articleList = mutableListOf<ArticleModel>()

    private val listener = object: ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

            val articleModel = snapshot.getValue(ArticleModel::class.java)
            articleModel ?: return

            articleList.add(articleModel)
            articleAdapter.submitList(articleList)
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

        auth = FirebaseAuth.getInstance()
        articleList.clear()
        articleAdapter = ArticleAdapter(onItemClicked = { model ->
            val directions : NavDirections = HomeFragmentDirections.
            actionHomeFragment5ToChatsFragment2(model.date)
            createChatsDB(model)
            findNavController().navigate(directions)
        })
        binding.MenuListView.layoutManager = LinearLayoutManager(context)
        binding.MenuListView.adapter = articleAdapter
        articleDB.addChildEventListener(listener)
    
        binding.goAddArticleButton.setOnClickListener{
            val directions : NavDirections = HomeFragmentDirections.
            actionHomeFragment5ToAddArticleFragment()
            findNavController().navigate(directions)
        }

        return binding.root
    }
    private fun createChatsDB(articleModel: ArticleModel){
        if (auth.currentUser?.uid != articleModel.sellerId) {

            userDB.child(auth.currentUser!!.uid)
                .child("chat")
                .push()
                .setValue(articleModel)

            userDB.child(articleModel.sellerId)
                .child("chat")
                .push()
                .setValue(articleModel)

            Snackbar.make(view!!, "채팅방이 생성되었습니다. 채팅탭에서 확인해주세요.", Snackbar.LENGTH_LONG).show()

        } else {
            Snackbar.make(view!!, "내가 올린 게시물입니다", Snackbar.LENGTH_LONG).show()
        }
    }
}