package com.example.happysejong.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.happysejong.databinding.ItemChatsReceiverBinding
import com.example.happysejong.databinding.ItemChatsSenderBinding
import com.example.happysejong.model.ChatModel
import com.example.happysejong.utils.ChatUtils.Companion.VIEW_TYPE_MESSAGE_RECEIVED
import com.example.happysejong.utils.ChatUtils.Companion.VIEW_TYPE_MESSAGE_SENT
import com.example.happysejong.utils.DBKeys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter : ListAdapter<ChatModel, RecyclerView.ViewHolder>(diffUtil){

    private var auth: FirebaseAuth = Firebase.auth
    private val timeFormat = SimpleDateFormat("HH시 mm분")
    //private val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일")

    inner class ReceivedMessageHolder(private val binding: ItemChatsReceiverBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(chatModel: ChatModel){
            val date = Date(chatModel.createdAt)
            binding.textGchatTimestampOther.text = timeFormat.format(date).toString()
            binding.textGchatUserOther.text = chatModel.users.nickName
            binding.textGchatMessageOther.text = chatModel.message
        }
    }

    inner class SentMessageHolder(private val binding: ItemChatsSenderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(chatModel: ChatModel){
            val date = Date(chatModel.createdAt)
            binding.textGchatTimestampMe.text = timeFormat.format(date).toString()
            binding.textGchatMessageMe.text = chatModel.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(currentList[position].users.uid == auth.currentUser?.uid){
            VIEW_TYPE_MESSAGE_SENT;
        } else{
            VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == VIEW_TYPE_MESSAGE_RECEIVED){
            ReceivedMessageHolder(ItemChatsReceiverBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }else {
            SentMessageHolder(ItemChatsSenderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.itemViewType == VIEW_TYPE_MESSAGE_SENT){
            val sentMessageHolder = holder as SentMessageHolder
            sentMessageHolder.bind(currentList[position])
        }else if(holder.itemViewType == VIEW_TYPE_MESSAGE_RECEIVED){
            val receivedHolder = holder as ReceivedMessageHolder
            receivedHolder.bind(currentList[position])
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatModel>() {
            override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
                return oldItem.createdAt == newItem.createdAt
            }
            override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}