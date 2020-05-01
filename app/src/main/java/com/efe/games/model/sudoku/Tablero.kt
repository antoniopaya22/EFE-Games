package com.efe.games.model.sudoku

import com.efe.games.business.sudoku.listeners.OnChangeListener

/**
 * Tablero de un sudoku
 *
 * Esta compuesto por:
 *  - Una matriz de celdas
 *  - Lista de sectores
 *  - Lista de filas
 *  - Lista de columnas
 */
class Tablero(
    var celdas: Array<Array<Celda>>,
    private var sectores: Array<GrupoCeldas>,
    private var filas: Array<GrupoCeldas>,
    private var columnas: Array<GrupoCeldas>
) {

    /**
     *  ====================================================
     *                      PROPIEDADES
     *  ====================================================
     */
    private var onChangeEnabled = true
    private var onChangeListeners: MutableList<OnChangeListener> = mutableListOf()

    /**
     *  ====================================================
     *                      STATIC
     *  ====================================================
     */

    companion object {
        const val SUDOKU_SIZE = 9

        fun crearTableroFromArray(tablero: Array<Array<Int>>): Tablero {
            val filas = Array(9) { GrupoCeldas() }
            val columnas = Array(9) { GrupoCeldas() }
            val sectores = Array(9) { GrupoCeldas() }
            val celdas: Array<Array<Celda>> = Array(9) { i -> Array(9) { j ->
                Celda(
                    i, j, tablero[i][j], tablero[i][j] == 0, true,
                    NotaCelda(), filas[j], columnas[i], sectores[((j / 3) * 3) + (i / 3)]
                )
            } }
            for (i in 0..8) for (j in 0..8) {
                filas[j].addCelda(celdas[i][j])
                columnas[i].addCelda(celdas[i][j])
                sectores[((j / 3) * 3) + (i / 3)].addCelda(celdas[i][j])
            }
            return Tablero(celdas, sectores, filas, columnas)
        }
    }

    /**
     *  ====================================================
     *                      FUNCIONES
     *  ====================================================
     */
    private fun setTodasCeldasInvalid() {
        onChangeEnabled = false
        for (r in 0 until SUDOKU_SIZE) for (c in 0 until SUDOKU_SIZE) {
            celdas[r][c].isValido = true
        }
        onChangeEnabled = true
        onChange()
    }

    fun isCompleted(): Boolean {
        for (i in 0..8) for (j in 0..8) {
            if (celdas[i][j].value == 0 || !celdas[i][j].isValido)
                return false
        }
        return true
    }

    fun getValoresUsados(): Map<Int, Int>? {
        val valuesUseCount: MutableMap<Int, Int> = HashMap()
        for (value in 1..SUDOKU_SIZE) {
            valuesUseCount[value] = 0
        }
        for (r in 0 until SUDOKU_SIZE) {
            for (c in 0 until SUDOKU_SIZE) {
                val value: Int = celdas[r][c].value
                if (value != 0) {
                    valuesUseCount[value] = valuesUseCount[value]!! + 1
                }
            }
        }
        return valuesUseCount
    }

    fun validar(): Boolean {
        var valido = true
        setTodasCeldasInvalid()
        onChangeEnabled = false
        filas.forEach { x -> if (!x.valido()) valido = false }
        columnas.forEach { x -> if (!x.valido()) valido = false }
        sectores.forEach { x -> if (!x.valido()) valido = false }
        onChangeEnabled = true
        onChange()
        return valido
    }

    /**
     *  ====================================================
     *                      LISTENERS
     *  ====================================================
     */

    fun addOnChangeListener(listener: OnChangeListener) = synchronized(onChangeListeners)
    {
        onChangeListeners.add(listener)
    }

    private fun onChange() {
        if(onChangeEnabled){
            synchronized(onChangeListeners) {
                onChangeListeners.forEach { x -> x.onChange() }
            }
        }
    }
}
