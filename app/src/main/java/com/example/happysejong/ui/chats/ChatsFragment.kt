package com.example.happysejong.ui.chats

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
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
import java.util.*
import java.util.jar.Manifest

class ChatsFragment : Fragment() {

    private val binding by lazy{ FragmentChatsBinding.inflate(layoutInflater)}

    private lateinit var chatDB: DatabaseReference
    private val auth = FirebaseAuth.getInstance()

    lateinit var chatsKeyViewModel: ChatsKeyViewModel
    private var chatKey = auth.currentUser!!.uid

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

        chatsKeyViewModel = ViewModelProvider(requireActivity()).get(ChatsKeyViewModel::class.java)

        chatsKeyViewModel.chatKey.observe(this){
            chatKey = it
            chatDB = Firebase.database.reference.child(DB_CHATS).child(chatKey)
            getChats()
        }

        userDB.addValueEventListener(currentUserDBListener)

        initAddChatButton()
        initGalleryButton()
        initTossButton()

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

    private fun initGalleryButton(){
        binding.galleryButton.setOnClickListener{
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                -> {
                    selectImageFromGallery()
                }
                else -> {
                    requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
    }

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            uri: Uri? -> uri?.let{

        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                selectImageFromGallery()
            } else {
                Toast.makeText(context, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
            }
        }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private fun initTossButton(){
        binding.tossButton.setOnClickListener{

        }
    }

}