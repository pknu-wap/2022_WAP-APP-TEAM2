package com.example.happysejong.model

data class ChatModel (
    val users: UserModel,
    val message: String,
    val isImage: Boolean,
    val isToss: Boolean,
    val createdAt: Long
        ){
    constructor(): this(UserModel("","","",0),"", 0)
}