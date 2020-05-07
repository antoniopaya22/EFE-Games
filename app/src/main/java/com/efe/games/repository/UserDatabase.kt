package com.efe.games.repository

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.efe.games.App
import com.efe.games.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO

    companion object {
        private const val DATABASE_NAME = "database"
        val instance by lazy {
            Room.databaseBuilder(
                App.instance,
                UserDatabase::class.java,
                DATABASE_NAME
            ).allowMainThreadQueries().build()
        }
    }
}