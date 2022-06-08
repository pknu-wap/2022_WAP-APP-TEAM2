package com.example.happysejong.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happysejong.adapter.ArticleAdapter
import com.example.happysejong.databinding.FragmentHomeBinding
import com.example.happysejong.model.ArticleModel
import com.example.happysejong.ui.chats.ChatsKeyViewModel
import com.example.happysejong.utils.DBKeys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val binding by lazy{ FragmentHomeBinding.inflate(layoutInflater)}

    private lateinit var chatsKeyViewModel: ChatsKeyViewModel

    private lateinit var articleAdapter: ArticleAdapter
    private val articleList = mutableListOf<ArticleModel>()

    private lateinit var auth: FirebaseAuth

    private val articleDB : DatabaseReference by lazy{
        Firebase.database.reference.child(DBKeys.DB_ARTICLES)
    }

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

        chatsKeyViewModel = ViewModelProvider(requireActivity()).get(ChatsKeyViewModel::class.java)

        auth = FirebaseAuth.getInstance()

        connectAdapter2ArticleRecyclerView()
    
        binding.goAddArticleButton.setOnClickListener{
            duplicatedSellerUid()
        }

        return binding.root
    }
    private fun connectAdapter2ArticleRecyclerView(){
        articleList.clear()
        articleAdapter = ArticleAdapter(onItemClicked = { model ->
            val directions : NavDirections = HomeFragmentDirections.
            actionHomeFragment5ToChatsFragment2()
            findNavController().navigate(directions)
            chatsKeyViewModel.setKey(model.sellerId)
        })
        binding.MenuListView.layoutManager = LinearLayoutManager(context)
        binding.MenuListView.adapter = articleAdapter
        articleDB.addChildEventListener(listener)
    }
    private fun duplicatedSellerUid(){
        val userId = auth.currentUser!!.uid
        CoroutineScope(Dispatchers.IO).launch {
            articleDB.child(userId).get().addOnSuccessListener {
                if (it.value.toString() == "null") {
                    go2AddArticleScreen()
                }else{
                    Toast.makeText(activity, "이미 생성된 게시물이 있습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun go2AddArticleScreen(){
        val directions: NavDirections =
            HomeFragmentDirections.actionHomeFragment5ToAddArticleFragment()
        findNavController().navigate(directions)
    }
}