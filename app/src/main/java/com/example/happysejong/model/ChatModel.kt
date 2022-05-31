package com.example.happysejong.model

data class ChatModel (
    val users: UserModel,
    val message: String,
    val createdAt: Long
        )