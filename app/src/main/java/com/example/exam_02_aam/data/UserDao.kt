package com.example.exam_02_aam.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.exam_02_aam.entities.User

@Dao
interface UserDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertUser(user: User) // Método para guardar un usuario

  @Query("SELECT * FROM users")
  suspend fun getAllUsers(): List<User> // Obtener todos los usuarios almacenados

  @Query("DELETE FROM users")
  suspend fun clearUsers()

}


