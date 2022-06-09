package com.example.happysejong.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.happysejong.model.ArticleModel
import com.example.happysejong.databinding.FragmentAddArticleBinding
import com.example.happysejong.utils.DBKeys
import com.example.happysejong.utils.DBKeys.Companion.DB_ARTICLES
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddArticleFragment : Fragment() {

    private val binding by lazy{ FragmentAddArticleBinding.inflate(layoutInflater)}


    private val articleDB : DatabaseReference by lazy {
        Firebase.database.reference.child(DB_ARTICLES).child(auth.currentUser!!.uid)
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        auth = FirebaseAuth.getInstance()
        initSelectCategoryButton()
        initAddArticleButton()
        return binding.root
    }

    private fun initSelectCategoryButton(){ // 카테고리 AlertDialog
        var foodArray: Array<String> = arrayOf("치킨", "피자", "한식", "일식", "중식", "양식", "패스트푸드", "기타")
        binding.selectCategoryButton.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("카테고리 선택")
                .setItems(foodArray,
                    DialogInterface.OnClickListener{ dialog, which ->
                        binding.selectCategoryButton.text = foodArray[which]
                    })
            builder.show()
        }
    }
    private fun initAddArticleButton(){
        binding.addArticleButton.setOnClickListener {
            val title = binding.articleTitleTextView.text.toString()
            val category = binding.selectCategoryButton.text.toString()
            val location = binding.articleLocationTextView.text.toString()
            val content = binding.articleTextTextView.text.toString()

            if (title.isNotEmpty() && category.isNotEmpty() && location.isNotEmpty() && content.isNotEmpty()){ // empty체크만 하고 DB로 올리는 코드는 쓰지 않음
                val model = ArticleModel(auth.uid.toString(), title, content, category, location, System.currentTimeMillis())
                articleDB.setValue(model)
                val direction : NavDirections = AddArticleFragmentDirections.actionAddArticleFragmentToHomeFragment5()
                findNavController().navigate(direction)
            }
        }
    }
}