package com.example.happysejong.ui.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.happysejong.databinding.FragmentSignUpBinding
import com.example.happysejong.model.UserModel
import com.example.happysejong.utils.DBKeys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SignUpFragment : Fragment() {

    private val binding by lazy{ FragmentSignUpBinding.inflate(layoutInflater)}

    private lateinit var auth: FirebaseAuth

    private val currentUserDB : DatabaseReference by lazy{
        Firebase.database.reference.child(DBKeys.DB_USERS)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        auth = Firebase.auth
        initSignUpButton()
        return binding.root
    }
    private fun initSignUpButton(){
        binding.signUpButton.setOnClickListener {
            val email = binding.registerEmailEditText.text.toString()
            val password = binding.registerPasswordEditText.text.toString()
            val confirm = binding.registerConfirmEditText.text.toString()
            val nickname = binding.registerNickNameEditText.text.toString()
            val dormitory = "행복 기숙사"

            if (password == confirm) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            successSignUpHandle(nickname, dormitory)
                        } else {
                            Toast.makeText(activity,
                                "회원가입이 실패하였습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(activity,
                    "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun successSignUpHandle(nickName: String, dormitory: String){
        Toast.makeText(activity, "회원가입 되었습니다.", Toast.LENGTH_SHORT).show()
        val userId = auth.currentUser?.uid.orEmpty()
        val model = UserModel(nickName, dormitory)
        currentUserDB.child(userId).setValue(model)
        val directions: NavDirections = SignUpFragmentDirections.
        actionSignUpFragmentToLoginFragment()
        findNavController().navigate(directions)
    }
}