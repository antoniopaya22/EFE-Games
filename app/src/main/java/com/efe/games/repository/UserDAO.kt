package com.efe.games.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.efe.games.model.User

@Dao
interface UserDAO {
    @Query("SELECT * from users")
    fun getAll(): List<User>

    @Insert(onConflict = REPLACE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("UPDATE users SET username =:username, puntos =:puntos WHERE id =:userId")
    fun update(userId: Long, username:String, puntos:Long)
}