package com.example.exam_02_aam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exam_02_aam.entities.User

class UsuarioAdapter(
    private val usuarios: List<User>,
    private val onUsuarioClick: (User) -> Unit,
    private val onUsuarioLongClick: (User) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvCorreo: TextView = itemView.findViewById(R.id.tvCorreo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuarios, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuarios[position]
        holder.tvUsername.text = usuario.username
        holder.tvNombre.text = usuario.name
        holder.tvCorreo.text = usuario.email

        holder.itemView.setOnClickListener { onUsuarioClick(usuario) }
        holder.itemView.setOnLongClickListener {
            onUsuarioLongClick(usuario)
            true
        }
    }


    override fun getItemCount(): Int = usuarios.size
}

