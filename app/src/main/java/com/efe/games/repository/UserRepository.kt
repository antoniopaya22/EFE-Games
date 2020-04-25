package com.efe.games.repository

import com.efe.games.model.User

object UserRepository: Repository<User> {

    override val elements = mutableListOf<User>(
        User(1, "antonio", "anto"),
        User(2, "alba", "alba")
    )

    fun getById(id: Long): User {
        return elements.first { x -> x.id.equals(id) }
    }

    fun update(element: User): User {
        return elements.set(elements.indexOfFirst { x -> x.id.equals(element.id) }, element)
    }

}