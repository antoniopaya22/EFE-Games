package com.efe.games.controller

import com.efe.games.model.User
import com.efe.games.repository.UserRepository

object UserController: Controller<User> {
    override var repository = UserRepository
    fun getById(id: Long) = repository.getById(id)
    fun update(element: User) = repository.update(element)
}