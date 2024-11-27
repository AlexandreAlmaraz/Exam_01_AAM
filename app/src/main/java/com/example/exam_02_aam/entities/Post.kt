package com.example.exam_02_aam.entities


data class Post(
    val id: Long=0,
    val userId: Int,
    val title: String,
    val body: String
)