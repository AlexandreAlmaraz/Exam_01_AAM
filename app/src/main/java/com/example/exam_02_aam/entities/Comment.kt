package com.example.exam_02_aam.entities

data class Comment(
    val id: Long=0,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)