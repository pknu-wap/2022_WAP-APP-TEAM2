package com.example.happysejong.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.happysejong.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private val binding by lazy{ FragmentLoginBinding.inflate(layoutInflater)}

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
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

    }
    private fun initSignUpTextView(){
        binding.signUpTextView.setOnClickListener{
            val directions: NavDirections = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(directions)
        }
    }
}