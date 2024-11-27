package com.example.exam_02_aam.service


import com.example.exam_02_aam.entities.Comment
import com.example.exam_02_aam.entities.Post
import com.example.exam_02_aam.entities.User
import retrofit2.http.GET
import retrofit2.http.Query

// ApiService.kt
interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("posts")
    suspend fun getPostsByUser(@Query("userId") userId: Int): List<Post>

    @GET("comments")
    suspend fun getCommentsByPost(@Query("postId") postId: Int): List<Comment>
}