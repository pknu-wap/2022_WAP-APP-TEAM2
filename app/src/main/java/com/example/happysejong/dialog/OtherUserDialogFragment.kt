package com.example.happysejong.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.happysejong.databinding.DialogOtherUserFragmentBinding
import com.example.happysejong.model.UserModel
import com.example.happysejong.ui.chats.ChatsFragmentArgs
import com.example.happysejong.utils.DBKeys
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OtherUserDialogFragment : DialogFragment() {

    private val binding by lazy{ DialogOtherUserFragmentBinding.inflate(layoutInflater)}
    private val userDB : DatabaseReference by lazy{
        Firebase.database.reference.child(DBKeys.DB_USERS)}

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val args: OtherUserDialogFragmentArgs by navArgs()
        val userItem = args.userModel

        userItemBinding(userItem)
        initReportButton(userItem)

        return binding.root
    }

    private fun userItemBinding(userItem : UserModel){

        binding.dialogOtherUserName.text = userItem.nickName
        binding.dialogOtherUserDormitory.text = userItem.dormitory
    }
    private fun initReportButton(userItem: UserModel){
        binding.reportButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                    userDB.child(userItem.uid).child("reports").get().addOnSuccessListener {
                        val reports = it.value.toString().toLong() + 1
                        userDB.child(userItem.uid).child("reports").setValue(reports)
                        dismiss()
                }
            }
        }
    }
}