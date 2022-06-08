package com.example.happysejong.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.happysejong.databinding.ItemChatsReceiverBinding
import com.example.happysejong.databinding.ItemChatsSenderBinding
import com.example.happysejong.model.ChatModel
import com.example.happysejong.model.UserModel
import com.example.happysejong.utils.App
import com.example.happysejong.utils.ChatUtils.Companion.VIEW_TYPE_MESSAGE_RECEIVED
import com.example.happysejong.utils.ChatUtils.Companion.VIEW_TYPE_MESSAGE_SENT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(val onItemClicked: (UserModel) -> Unit) : ListAdapter<ChatModel, RecyclerView.ViewHolder>(diffUtil){

    private var auth: FirebaseAuth = Firebase.auth
    private val timeFormat = SimpleDateFormat("HH시 mm분")
    //private val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일")

    inner class ReceivedMessageHolder(private val binding: ItemChatsReceiverBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(chatModel: ChatModel){
            val date = Date(chatModel.createdAt)
            binding.textGchatTimestampOther.text = timeFormat.format(date).toString()
            binding.textGchatUserOther.text = chatModel.users.nickName
            if(chatModel.isImage){
                binding.imageOther.visibility = View.VISIBLE
                binding.textGchatMessageOther.visibility = View.GONE
                Glide.with(App.applicationContext()).load(chatModel.message.toUri()).into(binding.imageOther)
            }else {
                binding.imageOther.visibility = View.GONE
                binding.textGchatMessageOther.visibility = View.VISIBLE
                binding.textGchatMessageOther.text = chatModel.message
            }
            binding.textGchatUserOther.setOnClickListener{
                onItemClicked(chatModel.users)
            }
        }
    }

    inner class SentMessageHolder(private val binding: ItemChatsSenderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(chatModel: ChatModel){
            val date = Date(chatModel.createdAt)
            binding.textGchatTimestampMe.text = timeFormat.format(date).toString()
            if(chatModel.isImage){
                binding.imageMe.visibility = View.VISIBLE
                binding.textGchatMessageMe.visibility = View.GONE
                Glide.with(App.applicationContext()).load(chatModel.message.toUri()).into(binding.imageMe)
            }else {
                binding.imageMe.visibility = View.GONE
                binding.textGchatMessageMe.visibility = View.VISIBLE
                binding.textGchatMessageMe.text = chatModel.message
            }
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