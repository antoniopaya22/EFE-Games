package com.efe.games.repository

import com.efe.games.model.User
import java.util.NoSuchElementException

object UserRepository {

    private val userDAO: UserDAO? = UserDatabase.instance.userDao()
    private var elements: MutableList<User> = userDAO!!.getAll().toMutableList()

    fun getById(id: Long): User? {
        try {
            return elements.first { x -> x.id == id }
        } catch (e: NoSuchElementException) {
            return null
        }
    }

    fun getByUsername(username: String): User? {
        try {
            return elements.first { x -> x.username == username }
        }catch (e: NoSuchElementException) {
            return null
        }
    }

    fun getAll(): List<User> {
        return elements
    }

    fun add(element: User) {
        userDAO!!.insert(element)
        elements = userDAO!!.getAll().toMutableList()
    }

    fun update(element: User) {
        userDAO!!.update(element.id, element.username, element.puntos)
        elements = userDAO!!.getAll().toMutableList()
    }

    fun delete(element: User) {
        userDAO!!.delete(element)
        elements = userDAO!!.getAll().toMutableList()
    }

}