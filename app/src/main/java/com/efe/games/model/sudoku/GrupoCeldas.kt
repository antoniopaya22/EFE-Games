package com.efe.games.model.sudoku

/**
 * Grupo de celdas del tablero
 *
 * Secores | Filas | Columnas
 */
class GrupoCeldas(
    private var celdas: MutableList<Celda> = mutableListOf()
) {

    /**
     *  ====================================================
     *                      FUNCIONES
     *  ====================================================
     */

    fun addCelda(celda: Celda): Boolean = celdas.add(celda)
    fun contains(num: Int): Boolean = celdas.find { x -> x.value == num } == null

    /**
     * Devuelve True si el grupo es valido (no hay numeros repetidos)
     * @return Boolean
     */
    fun valido(): Boolean {
        var valido = true
        val celdasValor: MutableMap<Int, Celda?> = HashMap()
        for (celda in celdas) {
            val value: Int = celda.value
            if (celdasValor.containsKey(value)) {
                celda.isValido = false
                celdasValor[value]!!.isValido = false
                valido = false
            } else {
                celdasValor[value] = celda
            }
        }
        return valido
    }
}