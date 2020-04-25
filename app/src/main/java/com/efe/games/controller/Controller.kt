package com.efe.games.controller

import com.efe.games.repository.Repository

interface Controller<T> {
    val repository: Repository<T>

    fun getAll(): MutableList<T> = repository.getAll()
    fun add(element: T) = repository.add(element)
    fun delete(element: T): Boolean = repository.delete(element)

}