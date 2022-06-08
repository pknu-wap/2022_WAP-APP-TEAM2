package com.example.happysejong.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.happysejong.databinding.DialogOtherUserFragmentBinding
import com.example.happysejong.ui.chats.ChatsFragmentArgs

class OtherUserDialogFragment : DialogFragment() {

    private val binding by lazy{ DialogOtherUserFragmentBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val args: OtherUserDialogFragmentArgs by navArgs()
        val userItem = args.userModel

        binding.dialogOtherUserName.text = userItem.nickName
        binding.dialogOtherUserDormitory.text = userItem.dormitory

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }

}