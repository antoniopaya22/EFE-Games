package com.efe.games.model.sudoku

import com.efe.games.model.sudoku.listeners.OnChangeListener


class Tablero(
    var celdas: Array<Array<Celda>>,
    var sectores: Array<GrupoCeldas>,
    var filas: Array<GrupoCeldas>,
    var columnas: Array<GrupoCeldas>,
    val size: Int
) {

    var onChangeListeners: MutableList<OnChangeListener> = mutableListOf()

    companion object {
        const val SUDOKU_SIZE = 9

        fun crearTableroVacio(): Tablero {
            var filas = Array(9) { GrupoCeldas() }
            var columnas = Array(9) { GrupoCeldas() }
            var sectores = Array(9) { GrupoCeldas() }
            var celdas = Array(9) { i ->
                Array(9) { j ->
                    Celda(
                        i, j, 0, true, true,
                        NotaCelda(), filas[j], columnas[i], sectores[((j / 3) * 3) + (i / 3)]
                    )
                }
            }
            return Tablero(celdas, sectores, filas, columnas, 9)
        }

        fun crearTableroFromArray(tablero: Array<Array<Int>>): Tablero {
            var filas = Array(9) { GrupoCeldas() }
            var columnas = Array(9) { GrupoCeldas() }
            var sectores = Array(9) { GrupoCeldas() }
            var celdas = Array(9) { i ->
                Array(9) { j ->
                    Celda(
                        i, j, tablero[i][j], tablero[i][j] == 0, true,
                        NotaCelda(), filas[j], columnas[i], sectores[((j / 3) * 3) + (i / 3)]
                    )
                }
            }
            return Tablero(celdas, sectores, filas, columnas, 9)
        }
    }

    fun setTodasCeldasEditables(): Unit = celdas.forEachIndexed { i, x ->
        x.forEachIndexed { j, y -> celdas[i][j].editable = true }
    }

    fun setTodasCeldasInvalid(): Unit = celdas.forEachIndexed { i, x ->
        x.forEachIndexed { j, y -> celdas[i][j].isValido = false }
    }

    fun isEmpty(): Boolean {
        for (i in 0..9) for (j in 0..9) if (celdas[i][j].value == 0) return false
        return true
    }

    fun isCompleted(): Boolean {
        for (i in 0..9) for (j in 0..9) {
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
        var valido: Boolean = true
        setTodasCeldasInvalid()
        filas.forEach { x -> if (!x.valido()) valido = false }
        columnas.forEach { x -> if (!x.valido()) valido = false }
        sectores.forEach { x -> if (!x.valido()) valido = false }
        return valido
    }

    // ================= LISTENERS ================

    fun addOnChangeListener(listener: OnChangeListener) = synchronized(onChangeListeners)
    {
        onChangeListeners.add(listener)
    }

    fun removeOnChangeListener(listener: OnChangeListener) = synchronized(onChangeListeners)
    {
        onChangeListeners.remove(listener)
    }

    fun onChange() {
        synchronized(onChangeListeners) {
            onChangeListeners.forEach { x -> x.onChange() }
        }
    }
}
