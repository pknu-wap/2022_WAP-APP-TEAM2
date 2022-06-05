package com.example.happysejong.ui.chats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happysejong.R
import com.example.happysejong.adapter.ChatAdapter
import com.example.happysejong.databinding.FragmentChatsBinding
import com.example.happysejong.model.ArticleModel
import com.example.happysejong.model.ChatModel
import com.example.happysejong.model.UserModel
import com.example.happysejong.utils.DBKeys.Companion.DB_CHATS
import com.example.happysejong.utils.DBKeys.Companion.DB_USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Collections.copy


class ChatsFragment : Fragment() {

    private val binding by lazy{ FragmentChatsBinding.inflate(layoutInflater)}

    private lateinit var chatDB: DatabaseReference
    private lateinit var userDB: DatabaseReference
    private lateinit var currentUserModel: UserModel

    private val chatList = mutableListOf<ChatModel>()
    private val adapter = ChatAdapter()

    private val currentUserDBListener = object: ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            currentUserModel = snapshot.getValue() as UserModel
        }
        override fun onCancelled(error: DatabaseError) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val args: ChatsFragmentArgs by navArgs()
        val chatKey = args.chatKey
        chatDB = Firebase.database.reference.child(DB_CHATS).child("$chatKey")
        chatDB.addValueEventListener(currentUserDBListener)

        userDB = Firebase.database.reference.child(DB_USERS)

        initAddChatButton()

        return binding.root
    }
    private fun initAddChatButton(){
        val message = binding.addChatsEditText.text.toString()
        val chatItem = ChatModel(currentUserModel, message, System.currentTimeMillis())
        chatDB.push().setValue(chatItem)
    }
    private fun getChats(){
        chatDB.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem = snapshot.getValue(ChatModel::class.java)
                chatItem ?: return
                chatList.add(chatItem)

                adapter.submitList(chatList)
                adapter.notifyDataSetChanged()

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
        binding.chatRecyclerView.adapter = adapter
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(context)
    }
}