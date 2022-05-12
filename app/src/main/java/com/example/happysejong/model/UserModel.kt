package com.example.happysejong.model

data class UserModel (
    val nickName: String,
    val dormitory: String
        ){
    constructor(): this("", "")
}