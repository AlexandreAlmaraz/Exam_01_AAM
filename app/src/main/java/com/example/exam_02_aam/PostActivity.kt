package com.example.exam_02_aam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exam_02_aam.entities.Post
import com.example.exam_02_aam.network.ClienteRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostActivity : AppCompatActivity() {

    private lateinit var rvPosts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        rvPosts = findViewById(R.id.rvUsarios)
        rvPosts.layoutManager = LinearLayoutManager(this)

        val userId = intent.getLongExtra("userId", -1)
        if (userId != -1L) {
            obtenerPosts(userId.toInt())
        } else {
            Toast.makeText(this, "Usuario no válido", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun obtenerPosts(userId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val posts: List<Post> =
                    ClienteRetrofit.getInstanciaRetrofit.getPostsByUser(userId)
                withContext(Dispatchers.Main) {
                    rvPosts.adapter = PostAdapter(posts) { post ->
                        val intent = Intent(this@PostActivity, CommentActivity::class.java)
                        intent.putExtra("postId", post.id)
                        startActivity(intent)
                    }
                }
            } catch (e: Exception) {
                Log.e("PostActivity", "Error al obtener posts", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@PostActivity, "Error al cargar los posts", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
