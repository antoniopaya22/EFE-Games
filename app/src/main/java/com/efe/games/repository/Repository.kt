package com.efe.games.repository

interface Repository<T> {
    val elements: MutableList<T>

    fun getAll(): MutableList<T> = elements
    fun add(element: T): Boolean = elements.add(element)
    fun delete(element: T): Boolean = elements.remove(element)
}