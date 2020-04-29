package com.efe.games.model.sudoku

import java.util.*

class NotaCelda () {

    //Atributos
    var numeros: Set<Int>

    init {
        this.numeros = Collections.unmodifiableSet(HashSet())
    }

    constructor(numeros: Set<Int>) : this() {
        this.numeros = Collections.unmodifiableSet(numeros)
    }

    fun isEmpty(): Boolean = numeros.isEmpty()

    fun addNumber(numero: Int): NotaCelda {
        require(!(numero < 1 || numero > 9)) { "El numero debe estar entre 1 y 9" }
        val aux: MutableSet<Int> = HashSet(numeros)
        aux.add(numero)
        return NotaCelda(aux)
    }

    fun clear(): NotaCelda {
        return NotaCelda()
    }

    fun anotarNumero(numero: Int): NotaCelda {
        require(!(numero < 1 || numero > 9)) { "El numero debe estar entre 1 y 9" }
        val numerosAnotados: MutableSet<Int> = HashSet(numeros)
        if (numerosAnotados.contains(numero)) {
            numerosAnotados.remove(numero)
        } else {
            numerosAnotados.add(numero)
        }
        return NotaCelda(numerosAnotados)
    }

}