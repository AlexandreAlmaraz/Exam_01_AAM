package com.example.exam_02_aam

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exam_02_aam.data.AppDatabase
import com.example.exam_02_aam.data.UserDao
import com.example.exam_02_aam.entities.User
import com.example.exam_02_aam.network.ClienteRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var rvUsuarios: RecyclerView
    private lateinit var userDao: UserDao // Declaración global de userDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvUsuarios = findViewById(R.id.rvUsarios)
        rvUsuarios.layoutManager = LinearLayoutManager(this)

        // Inicialización de la base de datos
        val database = AppDatabase.getDatabase(this)
        userDao = database.userDao()

        obtenerUsuarios()
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun obtenerUsuarios() {
        CoroutineScope(Dispatchers.IO).launch {
            if (isOnline(this@MainActivity)) {
                try {
                    val usuarios: List<User> = ClienteRetrofit.getInstanciaRetrofit.getUsers()
                    withContext(Dispatchers.Main) {
                        mostrarUsuarios(usuarios) // Solo mostramos usuarios descargados
                    }
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error al obtener usuarios", e)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Error al cargar los datos", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                val usuariosGuardados = userDao.getAllUsers()
                withContext(Dispatchers.Main) {
                    if (usuariosGuardados.isNotEmpty()) {
                        mostrarUsuarios(usuariosGuardados) // Mostrar usuarios guardados
                    } else {
                        Toast.makeText(this@MainActivity, "Sin conexión e información local", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun mostrarUsuarios(usuarios: List<User>) {
        rvUsuarios.adapter = UsuarioAdapter(usuarios, { usuario ->
            // Acción al hacer clic normal (mostrar posts del usuario)
            val intent = Intent(this, PostActivity::class.java)
            intent.putExtra("userId", usuario.id)
            startActivity(intent)
        }, { usuario ->
            // Acción al hacer clic largo (guardar un solo usuario en Room)
            CoroutineScope(Dispatchers.IO).launch {
                //userDao.clearUsers() // Limpia los datos antiguos
                userDao.insertUser(usuario) // Guarda el nuevo usuario
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Usuario guardado localmente", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

}
