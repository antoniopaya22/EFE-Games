package com.efe.games.model.sudoku

class GrupoCeldas(
    private var celdas: MutableList<Celda> = mutableListOf()
) {
    fun addCelda(celda: Celda): Boolean = celdas.add(celda)
    fun contains(num: Int): Boolean = celdas.find { x -> x.value == num } == null

    fun valido(): Boolean {
        val valorCeldas = HashMap<Int,Celda>()
        var valido = true
        for (x in celdas) if(valorCeldas.containsKey(x.value)){
            x.isValido = false
            valorCeldas[x.value]?.isValido = false
            valido = false
        }
        else valorCeldas[x.value] = x
        return valido
    }
}