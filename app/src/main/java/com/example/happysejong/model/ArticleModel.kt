package com.example.happysejong.model

data class ArticleModel (
    val title: String,
    val content: String,
    val date: Long
        ){
    constructor(): this("", " ", 0)
}