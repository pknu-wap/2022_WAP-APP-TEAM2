package com.example.happysejong.ui.users

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.happysejong.databinding.FragmentLoginBinding
import com.example.happysejong.ui.MainActivity
import com.example.happysejong.utils.DBKeys.Companion.DB_USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private val binding by lazy{ FragmentLoginBinding.inflate(layoutInflater)}

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        auth = FirebaseAuth.getInstance()
        initLoginButton()
        initEmailAndPasswordEditText()
        initSignUpTextView()
        return binding.root
    }
    private fun initLoginButton(){
        binding.loginButton.setOnClickListener{
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()){ task->
                    if(task.isSuccessful){
                        handleSuccessLogin()
                    }else{
                        Toast.makeText(activity, "입력 정보를 확인해주세요. ", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    private fun initEmailAndPasswordEditText(){
        binding.passwordEditText.addTextChangedListener{
            val enable = binding.emailEditText.text.isNotEmpty() and binding.passwordEditText.text.isNotEmpty()
            binding.loginButton.isEnabled = enable
        }
        binding.emailEditText.addTextChangedListener{
            val enable = binding.emailEditText.text.isNotEmpty() and binding.passwordEditText.text.isNotEmpty()
            binding.loginButton.isEnabled = enable
        }
    }
    private fun handleSuccessLogin(){
        if(auth.currentUser == null){
            Toast.makeText(activity, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }
    private fun initSignUpTextView(){
        binding.signUpTextView.setOnClickListener{
            val directions: NavDirections = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(directions)
        }
    }
}