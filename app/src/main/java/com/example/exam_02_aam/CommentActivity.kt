package com.example.exam_02_aam

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exam_02_aam.entities.Comment
import com.example.exam_02_aam.network.ClienteRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentActivity : AppCompatActivity() {

    private lateinit var rvComentarios: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        rvComentarios = findViewById(R.id.rvComentarios)
        rvComentarios.layoutManager = LinearLayoutManager(this)

        val postId = intent.getLongExtra("postId", -1)
        if (postId != -1L) {
            obtenerComentarios(postId.toInt())
        } else {
            Toast.makeText(this, "Post no válido", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun obtenerComentarios(postId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val comments: List<Comment> =
                    ClienteRetrofit.getInstanciaRetrofit.getCommentsByPost(postId)
                withContext(Dispatchers.Main) {
                    rvComentarios.adapter = CommentAdapter(comments)
                }
            } catch (e: Exception) {
                Log.e("CommentActivity", "Error al obtener comentarios", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@CommentActivity, "Error al cargar los comentarios", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
