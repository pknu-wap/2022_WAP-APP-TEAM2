package com.example.happysejong.ui.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ChatsKeyViewModel: ViewModel() {

    private val _chatKey = MutableLiveData<String>()

    val chatKey: LiveData<String> = _chatKey

    fun setKey(key: String) {
        viewModelScope.launch {
            _chatKey.value = key
        }
    }
}