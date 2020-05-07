package com.efe.games.controller

import com.efe.games.model.User
import com.efe.games.repository.UserRepository

object UserController {
    var repository = UserRepository
    var usuarioActual: User? = null

    fun init(username: String?) {
        if(username != null) usuarioActual = repository.getByUsername(username)
    }

    fun getById(id: Long) = repository.getById(id)
    fun getAll() = repository.getAll()

    fun update(element: User) = repository.update(element)
    fun delete(username: String) = repository.getByUsername(username)?.let { repository.delete(it) }
    fun addUser(username: String) {
        repository.add(User(
            0,
            username,
            0
        ))
        this.usuarioActual = repository.getByUsername(username)
    }
    fun addPuntos(puntos: Long) {
        usuarioActual!!.puntos += puntos
        this.update(usuarioActual!!)
    }
}