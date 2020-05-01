package com.efe.games.model.sudoku

import java.util.*

/**
 * Nota de una celda del tablero
 */
class NotaCelda () {

    /**
     *  ====================================================
     *                      PROPIEDADES
     *  ====================================================
     */
    var numeros: Set<Int>

    /**
     *  ====================================================
     *                  CONSTRUCTOR / INIT
     *  ====================================================
     */
    constructor(numeros: Set<Int>) : this() {
        this.numeros = Collections.unmodifiableSet(numeros)
    }

    init {
        this.numeros = Collections.unmodifiableSet(HashSet())
    }

    /**
     *  ====================================================
     *                      FUNCIONES
     *  ====================================================
     */

    fun isEmpty(): Boolean = numeros.isEmpty()

    fun addNumber(numero: Int): NotaCelda {
        val aux: MutableSet<Int> = HashSet(numeros)
        aux.add(numero)
        return NotaCelda(aux)
    }

    fun anotarNumero(numero: Int): NotaCelda {
        val numerosAnotados: MutableSet<Int> = HashSet(numeros)
        if (numerosAnotados.contains(numero))
            numerosAnotados.remove(numero)
        else
            numerosAnotados.add(numero)
        return NotaCelda(numerosAnotados)
    }

}