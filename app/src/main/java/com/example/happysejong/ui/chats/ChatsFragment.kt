package com.example.happysejong.ui.chats

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happysejong.adapter.ChatAdapter
import com.example.happysejong.databinding.FragmentChatsBinding
import com.example.happysejong.model.ChatModel
import com.example.happysejong.model.UserModel
import com.example.happysejong.utils.DBKeys.Companion.DB_CHATS
import com.example.happysejong.utils.DBKeys.Companion.DB_USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatsFragment : Fragment() {

    private val binding by lazy{ FragmentChatsBinding.inflate(layoutInflater)}

    private lateinit var chatDB: DatabaseReference
    private val auth = FirebaseAuth.getInstance()

    private lateinit var currentUserModel: UserModel
    private val userDB: DatabaseReference by lazy{
        Firebase.database.reference.child(DB_USERS).child(auth.currentUser!!.uid)
    }

    private val currentUserDBListener = object: ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            currentUserModel = snapshot.getValue(UserModel:: class.java)!!
        }
        override fun onCancelled(error: DatabaseError) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val args: ChatsFragmentArgs by navArgs()
        val chatKey = args.chatKey
        chatDB = Firebase.database.reference.child(DB_CHATS).child(chatKey)
        userDB.addValueEventListener(currentUserDBListener)

        getChats()
        initAddChatButton()

        return binding.root
    }
    private fun initAddChatButton(){
        binding.addChatButton.setOnClickListener{
            val message = binding.addChatsEditText.text.toString()
            val chatItem = ChatModel(currentUserModel, message, System.currentTimeMillis())
            chatDB.push().setValue(chatItem)
            binding.addChatsEditText.setText("")
        }
    }
    private fun getChats(){
        val chatList = mutableListOf<ChatModel>()

        val adapter = ChatAdapter(onItemClicked = { userModel ->
            val direction : NavDirections  = ChatsFragmentDirections
                .actionChatsFragmentToOtherUserDialogFragment(userModel)
            findNavController().navigate(direction)

        })

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