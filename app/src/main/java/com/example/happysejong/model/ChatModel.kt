package com.example.happysejong.model

data class ChatModel (
    val users: UserModel,
    val message: String,
    val createdAt: Long
        ){
    constructor(): this(UserModel("","","",0),"", 0)
}