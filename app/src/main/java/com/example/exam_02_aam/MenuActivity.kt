package com.example.exam_02_aam

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnUsuarios = findViewById<Button>(R.id.btnRecycler)
        val btnCreditos = findViewById<Button>(R.id.btnCreditos)
        val btnSalir = findViewById<Button>(R.id.btnSalir)

        btnUsuarios.setOnClickListener{
            val intentRecycler = Intent(this, MainActivity::class.java)
            startActivity(intentRecycler)
        }

        btnCreditos.setOnClickListener{
            val intentCreditos = Intent(this, CreditosActivity::class.java)
            startActivity(intentCreditos)
        }

        btnSalir.setOnClickListener{
            finishAndRemoveTask()
        }
    }

}