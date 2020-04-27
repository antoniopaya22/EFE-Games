package com.efe.games.model.sudoku

class NotaCelda (
    var numeros: Set<Int>,
    var numerosAnotados: Set<Int>
) {

    fun addNumber(num : Int){
        numeros = numeros.plus(num)
    }

    fun anotarNumero(num: Int) {
        if (numerosAnotados.contains(num)) numerosAnotados.minus(num) else numerosAnotados.plus(num)
    }
}